package com.fotova.firstapp.controller.category;

import com.fotova.dto.category.CategoryDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.category.CategoryService;
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


    @GetMapping("auth/category/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable int categoryId) {
        Response<CategoryDto> response = Response.<CategoryDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category retrieved successfully")
                .data(categoryService.getCategoryDtoById(categoryId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

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

    @DeleteMapping("auth/category/{id}/delete")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable int id) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category deleted successfully")
                .data(categoryService.deleteCategoryById(id))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}