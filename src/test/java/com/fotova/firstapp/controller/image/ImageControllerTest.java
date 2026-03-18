package com.fotova.firstapp.controller.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.image.ImageDto;
import com.fotova.dto.product.CategoryInnerProductDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.service.html.authentication.AuthHtmlService;
import com.fotova.service.image.ImageService;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ImageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImageService imageService;

    @MockitoBean
    private AuthHtmlService authHtmlService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all images")
    public void getAllImagesTest() throws Exception {
        // GIVEN
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1);
        imageDto.setPath("/images/product1.jpg");

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(10);
        productDtoBack.setName("Product Test");
        productDtoBack.setQuantity(5);
        productDtoBack.setPrice(99.99);
        productDtoBack.setCategoryInnerProductDto(new CategoryInnerProductDto());
        productDtoBack.setUrl("http://example.com/product.jpg");

        imageDto.setProductDtoBack(productDtoBack);

        List<ImageDto> imageDtoList = Collections.singletonList(imageDto);

        // WHEN
        BDDMockito.given(imageService.getAllImages()).willReturn(imageDtoList);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/images")
                .contentType("application/json"));

        // THEN
        verify(imageService, times(1)).getAllImages();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("Images retrieve successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id", CoreMatchers.is(imageDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].path", CoreMatchers.is(imageDto.getPath())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productDtoBack.id",
                        CoreMatchers.is(productDtoBack.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productDtoBack.name",
                        CoreMatchers.is(productDtoBack.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productDtoBack.price",
                        CoreMatchers.is(productDtoBack.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productDtoBack.quantity",
                        CoreMatchers.is(productDtoBack.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productDtoBack.url",
                        CoreMatchers.is(productDtoBack.getUrl())));
    }

    @Test
    @DisplayName("Get image by id")
    public void getImageByIdTest() throws Exception {
        // GIVEN
        Integer imageId = 1;
        ImageDto imageDto = new ImageDto();
        imageDto.setId(imageId);
        imageDto.setPath("/images/product1.jpg");

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(10);
        productDtoBack.setName("Product Test");
        productDtoBack.setQuantity(5);
        productDtoBack.setPrice(99.99);
        productDtoBack.setCategoryInnerProductDto(new CategoryInnerProductDto());
        productDtoBack.setUrl("http://example.com/product.jpg");

        imageDto.setProductDtoBack(productDtoBack);

        // WHEN
        BDDMockito.given(imageService.getImageById(imageId)).willReturn(imageDto);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/image/{id}", imageId)
                .contentType("application/json"));

        // THEN
        verify(imageService, times(1)).getImageById(imageId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("Image retrieve successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(imageDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.path", CoreMatchers.is(imageDto.getPath())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productDtoBack.id",
                        CoreMatchers.is(productDtoBack.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productDtoBack.name",
                        CoreMatchers.is(productDtoBack.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productDtoBack.price",
                        CoreMatchers.is(productDtoBack.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productDtoBack.quantity",
                        CoreMatchers.is(productDtoBack.getQuantity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productDtoBack.url",
                        CoreMatchers.is(productDtoBack.getUrl())));
    }
}
