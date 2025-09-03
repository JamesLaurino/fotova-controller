package com.fotova.firstapp.controller.stripe;

import com.fotova.dto.StripeResponse;
import com.fotova.dto.stripe.StripeProductRequest;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.StripeService;
import com.fotova.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*",methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Checkout the product basket")
    @ApiResponse(responseCode = "200", description = "Checkout if the basket is sync with the database product",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = StripeResponse.class))
            })
    @ApiResponse(responseCode = "500", description = "An error occur during the payment",
            content = @Content)
    @PostMapping("auth/checkout")
    public ResponseEntity<Object> checkoutProducts(@RequestBody StripeProductRequest productRequest) {

        productRequest = orderService.setStripeProductRequestName(productRequest);

        orderService.fillOrderBasketWithStripeRequest(productRequest);

        orderService.checkOrderQuantity(productRequest.getName());

        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);

        Response<StripeResponse> response = Response.<StripeResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Checkout realized with success")
                .data(stripeResponse)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Order after payment stripe")
    @ApiResponse(responseCode = "200", description = "Order made with success",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = StripeResponse.class))
            })
    @ApiResponse(responseCode = "500", description = "An error occur during the payment",
            content = @Content)
    @GetMapping("auth/{orderUUID}/success")
    public ResponseEntity<Object> success(@PathVariable String orderUUID){

        String orderRes = orderService.createOrderAfterShipment(orderUUID);

        if(!orderRes.equals("Order not created")) {
            orderService.sendRabbitMQOrder(orderRes);
            orderService.cleanOrderBasketByUUID(orderUUID);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(orderRes);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(orderRes);
    }

    @Operation(summary = "Order cancel notification")
    @ApiResponse(responseCode = "200", description = "Payment not ok",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @GetMapping("auth/cancel")
    public String cancel(){
        return "Payment not ok";
    }
}