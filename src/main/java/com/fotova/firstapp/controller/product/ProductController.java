package com.fotova.firstapp.controller.product;

import com.fotova.dto.product.ProductDtoBack;
import com.fotova.dto.product.ProductPageDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private Environment environment;

    @GetMapping( "env")
    public String getEnvironmentProperty() {
        return environment.getProperty("spring.application.name");
    }

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

    @GetMapping("auth/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        Response<ProductDtoBack> response = Response.<ProductDtoBack>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product retrieved successfully")
                .data(productService.getProductById(id))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/product/{categoryId}/add")
    public ResponseEntity<Object> addProduct(@PathVariable int categoryId, @RequestBody ProductDtoBack productDto) {
        Response<ProductDtoBack> response = Response.<ProductDtoBack>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Category added to a product successfully")
                .data(productService.saveProduct(productDto,categoryId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("auth/product/{id}/delete")
    public ResponseEntity<Object> deleteProductById(@PathVariable int id) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product deleted successfully")
                .data(productService.deleteProductById(id))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/product/update")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductDtoBack productDto) {
        Response<ProductDtoBack> response = Response.<ProductDtoBack>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product updated successfully")
                .data(productService.updateProduct(productDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("auth/drl/products")
    public String getAllProducts() {
        productService.testDroolsService();
        return "Drools Service";
    }

}