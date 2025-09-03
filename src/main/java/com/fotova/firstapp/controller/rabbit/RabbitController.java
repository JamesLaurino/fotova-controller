package com.fotova.firstapp.controller.rabbit;

import com.drools.dto.product.ProductDtoDrl;
import com.fotova.dto.contact.ContactDto;
import com.fotova.dto.order.OrderDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.product.ProductService;
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
public class RabbitController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Sending an email to the owner of the website")
    @ApiResponse(responseCode = "200", description = "Mail send successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ContactDto.class))
            })
    @ApiResponse(responseCode = "500", description = "There was an error sending the mail",
            content = @Content)
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
