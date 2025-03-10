package com.fotova.firstapp.controller.stripe;

import com.fotova.dto.stripe.StripeProductRequest;
import com.fotova.dto.StripeResponse;
import com.fotova.service.StripeService;
import com.fotova.service.order.OrderService;
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

    @PostMapping("auth/checkout")
    public ResponseEntity<Object> checkoutProducts(@RequestBody StripeProductRequest productRequest) {

        // SET NAME TO SPECIAL UUID
        productRequest = orderService.setStripeProductRequestName(productRequest);

        // FILL REDIS WITH STRIPE REQUEST
        orderService.fillOrderBasketWithStripeRequest(productRequest);

        // PROCESS THE ORDER
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(stripeResponse);
    }

    @GetMapping("auth/{orderUUID}/success")
    public ResponseEntity<Object> success(@PathVariable String orderUUID){

        String res = orderService.createOrderAfterShipment(orderUUID);

        if(!res.equals("Order not created")) {
            orderService.cleanOrderBasketByUUID(orderUUID);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(res);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(res);
    }

    @GetMapping("auth/cancel")
    public String cancel(){
        return "Payment not ok";
    }
}