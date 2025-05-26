package com.fotova.firstapp.controller.image;

import com.fotova.dto.image.ImageDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("auth/images")
    public ResponseEntity<Object> getAllImages() {
        Response<List<ImageDto>> response = Response.<List<ImageDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Images retrieve successfully")
                .data(imageService.getAllImages())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/image/{id}")
    public ResponseEntity<Object> getImageById(@PathVariable("id") Integer imageId) {
        Response<ImageDto> response = Response.<ImageDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Image retrieve successfully")
                .data(imageService.getImageById(imageId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("auth/image/{id}/delete")
    public ResponseEntity<Object> deleteImage(@PathVariable("id") Integer imageId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Images deleted successfully")
                .data(imageService.deleteImageById(imageId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/image/add/{id}")
    public ResponseEntity<Object> addImage(@RequestBody ImageDto imageDto, @PathVariable("id") Integer productId) {
        Response<ImageDto> response = Response.<ImageDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Image added successfully")
                .data(imageService.saveImage(imageDto,productId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/image/update")
    public ResponseEntity<Object> updateComment(@RequestBody ImageDto imageDto) {
        Response<ImageDto> response = Response.<ImageDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Image updated successfully")
                .data(imageService.updateImage(imageDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
