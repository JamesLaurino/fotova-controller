package com.fotova.firstapp.controller.rabbit;

import com.drools.dto.product.ProductDtoDrl;
import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class RabbitController {
    @Autowired
    private ProductService productService;


    @PostMapping("auth/amq/products")
    public String testAMQProduct(@RequestBody ProductDtoDrl productDto) {
        productService.testAMQPService(productDto);
        return "AMQP Service";
    }
}
