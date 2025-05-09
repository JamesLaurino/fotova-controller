package com.fotova.firstapp.service.category;

import com.fotova.dto.category.CategoryDto;
import com.fotova.entity.CategoryEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.service.category.CategoryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryMapperServiceUnitTest {

    private CategoryMapper categoryMapper = new CategoryMapper();

    @Test
    @DisplayName("Map category entity to category dto")
    public void toCategoryDto() {

        // GIVEN
        CategoryEntity categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setId(1);
        categoryEntityOne.setName("Category 1");
        categoryEntityOne.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        // WHEN
        CategoryDto categoryDtoRes = categoryMapper.toCategoryDto(categoryEntityOne);

        // THEN
        assertThat(categoryDtoRes).isNotNull();
        assertThat(categoryDtoRes.getId()).isEqualTo(categoryEntityOne.getId());
        assertThat(categoryDtoRes.getName()).isEqualTo(categoryEntityOne.getName());
    }

    @Test
    @DisplayName("Map category dto to category entity")
    public void toCategoryEntity() {

        // GIVEN
        CategoryDto categoryDtoOne = new CategoryDto();
        categoryDtoOne.setId(1);
        categoryDtoOne.setName("Category dto 1");

        // WHEN
        CategoryEntity categoryEntityRes = categoryMapper.toCategoryEntity(categoryDtoOne);

        // THEN
        assertThat(categoryEntityRes).isNotNull();
        assertThat(categoryEntityRes.getId()).isEqualTo(categoryDtoOne.getId());
        assertThat(categoryEntityRes.getName()).isEqualTo(categoryDtoOne.getName());

    }

    @Test
    @DisplayName("Map category entity list to category dto list")
    public void toCategoryDtoList() {
        // GIVEN
        CategoryEntity categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setId(1);
        categoryEntityOne.setName("Category 1");
        categoryEntityOne.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        CategoryEntity categoryEntityTwo = new CategoryEntity();
        categoryEntityTwo.setId(2);
        categoryEntityTwo.setName("Category 2");
        categoryEntityTwo.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        List<CategoryEntity> categoryEntityList = List.of(categoryEntityOne, categoryEntityTwo);

        // WHEN
        List<CategoryDto> categoryDtoListRes = categoryMapper.toCategoryDtoList(categoryEntityList);

        // THEN
        assertThat(categoryDtoListRes).isNotNull();
        assertThat(categoryDtoListRes).hasSize(2);

        categoryDtoListRes.forEach((categoryDto) -> {
            if(categoryDto.getId() == 1) {
                assertThat(categoryDto.getName()).isEqualTo(categoryEntityOne.getName());
                assertThat(categoryDto.getId()).isEqualTo(categoryEntityOne.getId());
            } else {
                assertThat(categoryDto.getName()).isEqualTo(categoryEntityTwo.getName());
                assertThat(categoryDto.getId()).isEqualTo(categoryEntityTwo.getId());
            }
        });

    }
}
