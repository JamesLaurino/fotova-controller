package com.fotova.firstapp.controller.category;

import com.fotova.dto.category.CategoryDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.category.CategoryService;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @Operation(summary = "Retrieve a category by its id")
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CategoryDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Category not found",
            content = @Content)
    @GetMapping("auth/category/{categoryId}")
    public ResponseEntity<Object> getCategoryById(
            @Parameter(description = "Category identifier - id", required = true, example = "1")
            @PathVariable int categoryId) {
        Response<CategoryDto> response = Response.<CategoryDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category retrieved successfully")
                .data(categoryService.getCategoryDtoById(categoryId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve all categories")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CategoryDto.class))
            })
    @GetMapping("auth/category")
    public ResponseEntity<Object> getAllCategory() {
        Response<List<CategoryDto>> response = Response.<List<CategoryDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Categories retrieved successfully")
                .data(categoryService.getAllCategories())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new category")
    @ApiResponse(responseCode = "200", description = "Category added successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CategoryDto.class))
            })
    @ApiResponse(responseCode = "409", description = "Category already exists",
            content = @Content)
    @PostMapping("auth/category/add")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDto categoryDto) {
        Response<CategoryDto> response = Response.<CategoryDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category added successfully")
                .data(categoryService.addCategory(categoryDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a category")
    @ApiResponse(responseCode = "200", description = "Category updated successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CategoryDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Category not found",
            content = @Content)
    @PutMapping("auth/category/update")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDto categoryDto) {
        Response<CategoryDto> response = Response.<CategoryDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category updated successfully")
                .data(categoryService.updateCategory(categoryDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a category by its id")
    @ApiResponse(responseCode = "200", description = "Category deleted successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @ApiResponse(responseCode = "404", description = "Category not found",
            content = @Content)
    @DeleteMapping("auth/category/{id}/delete")
    public ResponseEntity<Object> deleteCategoryById(
            @Parameter(description = "Category identifier - id", required = true, example = "1")
            @PathVariable int id) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category deleted successfully")
                .data(categoryService.deleteCategoryById(id))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}