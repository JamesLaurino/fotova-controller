package com.fotova.firstapp.controller.stripe;

import com.fotova.dto.StripeProductRequest;
import com.fotova.dto.StripeResponse;
import com.fotova.service.StripeService;
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

    @PostMapping("auth/checkout")
    public ResponseEntity<Object> checkoutProducts(@RequestBody StripeProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("auth/success")
    public String success(){
        return "payment ok";
    }

    @GetMapping("auth/cancel")
    public String cancel(){
        return "Payment not ok";
    }
}