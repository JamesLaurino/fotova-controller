package com.fotova.firstapp.controller.label;

import com.fotova.dto.label.LabelDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.label.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @GetMapping("auth/label")
    public ResponseEntity<Object> getLabels() {
        Response<List<LabelDto>> response = Response.<List<LabelDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("All labels has been retreived successfully.")
                .data(labelService.getAllLabels())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
