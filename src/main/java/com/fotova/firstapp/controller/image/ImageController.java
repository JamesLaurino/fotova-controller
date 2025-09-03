package com.fotova.firstapp.controller.image;

import com.fotova.dto.image.ImageDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Retrieve all images from database")
    @ApiResponse(responseCode = "200", description = "Images retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ImageDto.class))
            })
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

    @Operation(summary = "Retrieve a images by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Image not found for the given id",
                            content = @Content()
                    )
            })
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

    @Operation(summary = "Delete a image by his id",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Image deleted successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ImageDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Image not found for the given id",
                content = @Content()
        )
    })
    @DeleteMapping("auth/image/{id}/delete")
    public ResponseEntity<Object> deleteImage(
            @Parameter(description = "image id", required = true, example = "1")
            @PathVariable("id") Integer imageId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Images deleted successfully")
                .data(imageService.deleteImageById(imageId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a image from product with product id",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Image deleted successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ImageDto.class))
        )
    })
    @DeleteMapping("auth/image/delete/{imageName}/{productId}")
    public ResponseEntity<Object> deleteImageFromProduct(
            @Parameter(description = "image name", required = true, example = "test.jpg")
            @PathVariable("imageName") String imageName,
            @Parameter(description = "product id", required = true, example = "1")
            @PathVariable("productId") Integer productId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Images deleted successfully")
                .data(imageService.deleteImageByImageName(imageName,productId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a image with his id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image added successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    )
            })
    @PostMapping("auth/image/add/{id}")
    public ResponseEntity<Object> addImage(
            @RequestBody ImageDto imageDto,
            @Parameter(description = "image id", required = true, example = "1")
            @PathVariable("id") Integer productId) {
        Response<ImageDto> response = Response.<ImageDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Image added successfully")
                .data(imageService.saveImage(imageDto,productId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Image updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImageDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Image not found for the given id",
                            content = @Content()
                    )
            })
    @PutMapping("auth/image/update")
    public ResponseEntity<Object> updateImage(@RequestBody ImageDto imageDto) {
        Response<ImageDto> response = Response.<ImageDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Image updated successfully")
                .data(imageService.updateImage(imageDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
