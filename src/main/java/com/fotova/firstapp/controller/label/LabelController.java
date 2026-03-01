package com.fotova.firstapp.controller.label;

import com.fotova.dto.label.LabelDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.label.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("auth/label/productId/{productId}")
    public ResponseEntity<Object> findLabelByProductId(@PathVariable("productId") Integer productId) {
        Response<LabelDto> response = Response.<LabelDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Label has been retreived successfully.")
                .data(labelService.findLabelByProductId(productId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/add/label")
    public ResponseEntity<Object> updateLabel(@RequestBody LabelDto labelDto) {
        Response<LabelDto> response = Response.<LabelDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Label has been updated successfully.")
                .data(labelService.updateLabel(labelDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }


}
