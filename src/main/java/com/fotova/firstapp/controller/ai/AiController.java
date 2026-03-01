package com.fotova.firstapp.controller.ai;

import com.fotova.entity.ProductEntity;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.ai.AiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping("auth/ai/label")
    public ResponseEntity<Object> addCategory(@RequestBody @Valid ProductEntity product) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Label translate successfully")
                .data(aiService.translateTitleAndDescription(product))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
