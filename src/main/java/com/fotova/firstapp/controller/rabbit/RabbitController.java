package com.fotova.firstapp.controller.rabbit;

import com.drools.dto.product.ProductDtoDrl;
import com.fotova.dto.contact.ContactDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class RabbitController {
    @Autowired
    private ProductService productService;

    @PostMapping("auth/amq/contact")
    public ResponseEntity<Object> sendEmailFromContact(@RequestBody ContactDto contactDto) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Mail send successfully")
                .data(productService.sendMailFromContact(contactDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/amq/products")
    public String testAMQProduct(@RequestBody ProductDtoDrl productDto) {
        productService.testAMQPService(productDto);
        return "AMQP Service";
    }
}
