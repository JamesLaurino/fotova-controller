package com.fotova.firstapp.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.product.CategoryInnerProductDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.service.product.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all products")
    public void getAllProductsTest() throws Exception {
        // GIVEN
        List<ProductDtoBack> productDtoList = List.of(
                new ProductDtoBack(),
                new ProductDtoBack(),
                new ProductDtoBack()
        );

        // WHEN
        BDDMockito.given(productService.getAllProducts()).willReturn(productDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/products"));

        // THEN
        verify(productService, times(1)).getAllProducts();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(productDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Products retrieved successfully")));
    }

    @Test
    @DisplayName("Get product by id")
    public void getProductByIdTest() throws Exception {
        // GIVEN
        int productId = 1;
        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(productId);
        productDtoBack.setName("Product Name");
        productDtoBack.setQuantity(10);
        productDtoBack.setPrice(100.0);

        // WHEN
        BDDMockito.given(productService.getProductById(productId)).willReturn(productDtoBack);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/product/{id}", productId));

        // THEN
        verify(productService, times(1)).getProductById(productId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Product retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(productDtoBack.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        CoreMatchers.is(productDtoBack.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price",
                        CoreMatchers.is(productDtoBack.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity",
                        CoreMatchers.is(productDtoBack.getQuantity())));
    }

    @Test
    @DisplayName("Add product to category")
    public void addProductTest() throws Exception {
        // GIVEN
        int categoryId = 1;
        ProductDtoBack productDto = new ProductDtoBack();
        productDto.setId(1);
        productDto.setName("New Product");
        productDto.setQuantity(5);
        productDto.setPrice(50.0);

        // WHEN
        BDDMockito.given(productService.saveProduct(any(ProductDtoBack.class), eq(categoryId)))
                .willReturn(productDto);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/product/{categoryId}/add", categoryId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDto))
        );

        // THEN
        verify(productService, times(1)).saveProduct(any(ProductDtoBack.class), eq(categoryId));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Category added to a product successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(productDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        CoreMatchers.is(productDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price",
                        CoreMatchers.is(productDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity",
                        CoreMatchers.is(productDto.getQuantity())));
    }

    @Test
    @DisplayName("Delete product by id")
    public void deleteProductByIdTest() throws Exception {
        // GIVEN
        int productId = 1;
        String deletedMessage = "Product deleted with id: " + productId;

        // WHEN
        BDDMockito.given(productService.deleteProductById(productId)).willReturn(deletedMessage);

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/auth/product/{id}/delete", productId)
                .contentType("application/json")
        );

        // THEN
        verify(productService, times(1)).deleteProductById(productId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Product deleted successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        CoreMatchers.is(deletedMessage)));
    }

    @Test
    @DisplayName("Update product")
    public void updateProductTest() throws Exception {
        // GIVEN
        ProductDtoBack productDto = new ProductDtoBack();
        productDto.setId(1);
        productDto.setName("Updated Product");
        productDto.setQuantity(10);
        productDto.setPrice(15.0);
        productDto.setCategoryInnerProductDto(new CategoryInnerProductDto());
        productDto.setUrl("http://example.com/product");

        // WHEN
        BDDMockito.given(productService.updateProduct(any(ProductDtoBack.class)))
                .willReturn(productDto);

        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/product/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDto)));

        // THEN
        verify(productService, times(1)).updateProduct(any(ProductDtoBack.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Product updated successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(productDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        CoreMatchers.is(productDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity",
                        CoreMatchers.is(productDto.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.price",
                        CoreMatchers.is(productDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.url",
                        CoreMatchers.is(productDto.getUrl())));
    }


}
