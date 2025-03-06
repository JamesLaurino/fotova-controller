package com.fotova.firstapp.controller.stripe;

import com.fotova.dto.stripe.StripeProductRequest;
import com.fotova.dto.StripeResponse;
import com.fotova.service.StripeService;
import com.fotova.service.order.OrderService;
import com.fotova.service.order.ValidationOrderService;
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

        // FILL THE STATIC LIST
        ValidationOrderService.setOrderBasketMapList(productRequest);

        // PROCESS THE ORDER
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

    @GetMapping("auth/{orderUUID}/success")
    public String success(@PathVariable String orderUUID){

        //TODO creation of order
        System.out.println("orderUUID : "+ orderUUID);
        for(var temp : ValidationOrderService.orderBasketDtoList) {

            System.out.println("product numéro : " + orderUUID);
            System.out.println("Product Id : "+ temp.getProductId());
            System.out.println("Product quantity : "+ temp.getQuantity());
            System.out.println("Client email : "+ temp.getEmail());
        }

        ValidationOrderService.cleanOrderBasketDtoList();
        return "payment ok";
    }

    @GetMapping("auth/cancel")
    public String cancel(){
        return "Payment not ok";
    }
}