package com.fotova.firstapp.controller.product;

import com.fotova.dto.product.ProductDtoBack;
import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("auth/products")
    public List<ProductDtoBack> getProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("auth/product/{id}")
    public ProductDtoBack getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("auth/drl/products")
    public String getAllProducts()
    {
        productService.testDroolsService();
        return "Drools Service";
    }

}