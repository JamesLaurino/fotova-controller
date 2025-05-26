package com.fotova.firstapp.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.category.CategoryDto;
import com.fotova.service.category.CategoryService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all categories")
    public void GetAllCategories() throws Exception {
        // GIVEN
        CategoryDto categoryDtoOne = new CategoryDto();
        categoryDtoOne.setId(1);
        categoryDtoOne.setName("category 1");

        CategoryDto categoryDtoTwo = new CategoryDto();
        categoryDtoTwo.setId(2);
        categoryDtoTwo.setName("category 2");

        CategoryDto categoryDtoThree = new CategoryDto();
        categoryDtoThree.setId(3);
        categoryDtoThree.setName("category 3");

        List<CategoryDto> categoryDtoList = List.of(categoryDtoOne, categoryDtoTwo, categoryDtoThree);

        // WHEN
        BDDMockito.given(categoryService.getAllCategories()).willReturn(categoryDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/category"));

        // THEN
        verify(categoryService, times(1)).getAllCategories();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(categoryDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Categories retrieved successfully")));

    }

    @Test
    @DisplayName("Get category by id")
    public void getCategoryById() throws Exception {
        // GIVEN
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("category 1");

        // WHEN
        BDDMockito.given(categoryService.getCategoryDtoById(categoryDto.getId())).willReturn(categoryDto);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/category/{categoryId}",
                categoryDto.getId()));

        verify(categoryService, times(1)).getCategoryDtoById(categoryDto.getId());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(categoryDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        CoreMatchers.is(categoryDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                    CoreMatchers.is("Category retrieved successfully")));
    }

    @Test
    @DisplayName("Add a category")
    public void addCategory() throws Exception {
        // GIVEN
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("category 1");

        // WHEN
        BDDMockito.given(categoryService.addCategory(any(CategoryDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/category/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDto)));

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(categoryDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        CoreMatchers.is(categoryDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Category added successfully")));
    }

    @Test
    @DisplayName("Update category")
    public void updateCategory() throws Exception {
        // GIVEN
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("category 1");

        // WHEN
        BDDMockito.given(categoryService.updateCategory(any(CategoryDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/category/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDto)));

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(categoryDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        CoreMatchers.is(categoryDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Category updated successfully")));
    }

    @Test
    @DisplayName("Delete a cateogry")
    public void deleteCateogry() throws Exception {
        // GIVEN
        Integer categoryId = 1;

        // WHEN
        BDDMockito.given(categoryService.deleteCategoryById(categoryId))
                .willReturn("Category deleted successfully for id : " + categoryId);
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/auth/category/{id}/delete",
                categoryId));

        // THEN
        verify(categoryService, times(1)).deleteCategoryById(categoryId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        CoreMatchers.is("Category deleted successfully for id : " + categoryId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Category deleted successfully")));
    }
}