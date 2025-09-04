package com.fotova.firstapp.controller.product;

import com.fotova.dto.product.ProductDtoBack;
import com.fotova.dto.product.ProductPageDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;


    @Operation(summary = "Retrieve all products with pagination")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ProductPageDto.class))
            })
    @GetMapping("auth/products/page")
    public ResponseEntity<Object> getProductWithPagination(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "2") int pageSize) {

        ProductPageDto productPageDto = productService.getAllProductsPaginate(pageNo,pageSize);
        Response<List<ProductDtoBack>> response = Response.<List<ProductDtoBack>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Products paginate retrieved successfully")
                .data(productPageDto.getContent())
                .success(true)
                .totalData(productPageDto.getTotalData())
                .pageNumber(productPageDto.getPageNumber())
                .pageSize(productPageDto.getPageSize())
                .totalPage(productPageDto.getTotalPage())
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ProductDtoBack.class))
            })
    @GetMapping("auth/products")
    public ResponseEntity<Object> getProduct() {
        Response<List<ProductDtoBack>> response = Response.<List<ProductDtoBack>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Products retrieved successfully")
                .data(productService.getAllProducts())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve all products by category id")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ProductDtoBack.class))
            })
    @GetMapping("auth/products/category/{categoryId}")
    public ResponseEntity<Object> getProductByCategory(
            @Parameter(description = "Category identifier - id", required = true, example = "1")
            @PathVariable int categoryId) {
        Response<List<ProductDtoBack>> response = Response.<List<ProductDtoBack>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Products retrieved successfully")
                .data(productService.getAllProductByCategoryId(categoryId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve a product by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDtoBack.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found for the given id",
                            content = @Content()
                    )
            })
    @GetMapping("auth/product/{id}")
    public ResponseEntity<Object> getProductById(
            @Parameter(description = "Product identifier - id", required = true, example = "1")
            @PathVariable int id) {
        Response<ProductDtoBack> response = Response.<ProductDtoBack>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product retrieved successfully")
                .data(productService.getProductById(id))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new product in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product added successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDtoBack.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Product already exist for the given id",
                            content = @Content()
                    )
            })
    @PostMapping("auth/product/{categoryId}/add")
    public ResponseEntity<Object> addProduct(
            @Parameter(description = "Category identifier - id", required = true, example = "1")
            @PathVariable int categoryId, @RequestBody @Valid ProductDtoBack productDto) {
        Response<ProductDtoBack> response = Response.<ProductDtoBack>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category added to a product successfully")
                .data(productService.saveProduct(productDto,categoryId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a product by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found for the given id",
                            content = @Content()
                    )
            })
    @DeleteMapping("auth/product/{id}/delete")
    public ResponseEntity<Object> deleteProductById(
            @Parameter(description = "Product identifier - id", required = true, example = "1")
            @PathVariable int id) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product deleted successfully")
                .data(productService.deleteProductById(id))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing product",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDtoBack.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found for the given id",
                            content = @Content()
                    )
            })
    @PutMapping("auth/product/update")
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid ProductDtoBack productDto) {
        Response<ProductDtoBack> response = Response.<ProductDtoBack>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product updated successfully")
                .data(productService.updateProduct(productDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("auth/drl/products")
    public List<ProductDtoBack> getAllProducts() {
        return productService.setDiscountForProductDrools();
    }

}