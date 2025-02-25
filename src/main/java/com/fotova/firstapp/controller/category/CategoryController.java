package com.fotova.firstapp.controller.category;

import com.fotova.dto.category.CategoryDto;
import com.fotova.entity.CategoryEntity;
import com.fotova.service.category.CategoryMapper;
import com.fotova.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryMapper categoryMapper;

    @GetMapping("auth/category/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable int categoryId) {

        CategoryEntity categoryEntity = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryMapper.toCategoryDto(categoryEntity));
    }

    @GetMapping("auth/category")
    public ResponseEntity<Object> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("auth/category/add")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    @PutMapping("auth/category/update")
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

    @DeleteMapping("auth/category/{id}/delete")
    public ResponseEntity<String> deleteCategoryById(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}