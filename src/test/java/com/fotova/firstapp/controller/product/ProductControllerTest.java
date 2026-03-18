package com.fotova.firstapp.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.service.html.authentication.AuthHtmlService;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private AuthHtmlService authHtmlService;

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
}
