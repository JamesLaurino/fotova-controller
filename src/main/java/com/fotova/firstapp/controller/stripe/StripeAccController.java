package com.fotova.firstapp.controller.stripe;


import com.fotova.dto.StripeResponse;
import com.fotova.dto.stripe.StripeProductRequest;
import com.fotova.service.html.StripeHtmlService;
import com.fotova.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@Profile("acc")
@CrossOrigin(origins = "*",methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class StripeAccController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private StripeHtmlService stripeHtmlService;

    @Operation(summary = "Checkout the product basket")
    @ApiResponse(responseCode = "200", description = "Checkout if the basket is sync with the database product",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = StripeResponse.class))
            })
    @ApiResponse(responseCode = "500", description = "An error occur during the payment",
            content = @Content)
    @PostMapping("auth/checkout")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Object> checkoutProducts(@RequestBody StripeProductRequest productRequest) throws MessagingException {

        productRequest = orderService.setStripeProductRequestName(productRequest);

        orderService.fillOrderBasketWithStripeRequest(productRequest);

        orderService.checkOrderQuantity(productRequest.getName());
        orderService.checkOrderPrice(productRequest);

        String orderRes = orderService.createOrderAfterShipment(productRequest.getName());

        if(!orderRes.equals("Order not created")) {
            orderService.sendRabbitMQOrder(orderRes);
            orderService.sendBillingEmail(productRequest.getName());
            orderService.cleanOrderBasketByUUID(productRequest.getName());
            return ResponseEntity.ok("Paiement réalisé avec success");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(stripeHtmlService.buildFailureHtml());

    }
}
