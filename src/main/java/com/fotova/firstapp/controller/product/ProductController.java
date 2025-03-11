package com.fotova.firstapp.controller.product;

import com.fotova.dto.product.ProductDtoBack;
import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("auth/products")
    public ResponseEntity<Object> getProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("auth/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("auth/product/{categoryId}/add")
    public ResponseEntity<Object> addProduct(@PathVariable int categoryId, @RequestBody ProductDtoBack productDto) {
        return ResponseEntity.ok(productService.saveProduct(productDto,categoryId));
    }

    @DeleteMapping("auth/product/{id}/delete")
    public ResponseEntity<String> deleteProductById(@PathVariable int id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PutMapping("auth/product/update")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDtoBack productDto) {
        ProductDtoBack product = productService.updateProduct(productDto);
        return ResponseEntity.ok(product);
    }


    @GetMapping("auth/drl/products")
    public String getAllProducts() {
        productService.testDroolsService();
        return "Drools Service";
    }

}