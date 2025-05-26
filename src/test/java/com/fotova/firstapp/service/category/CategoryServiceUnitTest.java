package com.fotova.firstapp.service.category;

import com.fotova.dto.category.CategoryDto;
import com.fotova.entity.CategoryEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.repository.category.CategoryRepositoryImpl;
import com.fotova.service.category.CategoryMapper;
import com.fotova.service.category.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

@SpringBootTest
public class CategoryServiceUnitTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepositoryImpl categoryRepositoryImpl;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("Get category by id")
    public void getCategoryById() {
        // GIVEN
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("Category 1");
        categoryEntity.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        Integer categoryId = 1;

        // WHEN
        BDDMockito.given(categoryRepositoryImpl.findById(categoryId)).willReturn(categoryEntity);
        CategoryEntity category = categoryService.getCategoryById(categoryId);

        // THEN
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(categoryId);
        assertThat(category.getName()).isEqualTo("Category 1");
        assertThat(category.getProducts()).hasSize(2);
        verify(categoryRepositoryImpl, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("Get category dto by id")
    public void getCategoryDtoById() {
        // GIVEN
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1);
        categoryEntity.setName("Category 1");
        categoryEntity.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("Category dto 1");

        Integer categoryId = 1;

        // WHEN
        BDDMockito.given(categoryRepositoryImpl.findById(categoryId)).willReturn(categoryEntity);
        BDDMockito.given(categoryMapper.toCategoryDto(categoryEntity)).willReturn(categoryDto);
        CategoryDto categoryDtoRes = categoryService.getCategoryDtoById(categoryId);

        // THEN
        assertThat(categoryDtoRes).isNotNull();
        assertThat(categoryDtoRes.getId()).isEqualTo(categoryId);
        assertThat(categoryDtoRes.getName()).isEqualTo("Category dto 1");
        verify(categoryRepositoryImpl, times(1)).findById(categoryId);
        verify(categoryMapper, times(1)).toCategoryDto(categoryEntity);
    }

    @Test
    @DisplayName("Get all categories")
    public void getAllCategories() {

        // GIVEN
        CategoryEntity categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setId(1);
        categoryEntityOne.setName("Category 1");
        categoryEntityOne.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        CategoryEntity categoryEntityTwo = new CategoryEntity();
        categoryEntityTwo.setId(2);
        categoryEntityTwo.setName("Category 2");
        categoryEntityTwo.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        CategoryDto categoryDtoOne = new CategoryDto();
        categoryDtoOne.setId(1);
        categoryDtoOne.setName("Category dto 1");

        CategoryDto categoryDtoTwo = new CategoryDto();
        categoryDtoTwo.setId(2);
        categoryDtoTwo.setName("Category dto 2");

        List<CategoryEntity> categoryEntityList = List.of(categoryEntityOne, categoryEntityTwo);
        List<CategoryDto> categoryDtoList = List.of(categoryDtoOne, categoryDtoTwo);

        // WHEN
        BDDMockito.given(categoryRepositoryImpl.findAll()).willReturn(categoryEntityList);
        BDDMockito.given(categoryMapper.toCategoryDtoList(categoryEntityList)).willReturn(categoryDtoList);
        List<CategoryDto> categoryDtoListRes = categoryService.getAllCategories();

        // THEN
        categoryDtoList.forEach((categoryDto) -> {

            if(categoryDto.getId() == 1) {
                assertThat(categoryDto).isEqualTo(categoryDtoOne);
            }

            if(categoryDto.getId() == 2) {
                assertThat(categoryDto).isEqualTo(categoryDtoTwo);
            }
        });

        assertThat(categoryDtoListRes).isEqualTo(categoryDtoList);
        assertThat(categoryDtoListRes).hasSize(2);
        assertThat(categoryDtoListRes).isNotNull();
        verify(categoryRepositoryImpl, times(1)).findAll();
        verify(categoryMapper, times(1)).toCategoryDtoList(categoryEntityList);
    }

    @Test
    @DisplayName("Delete category by id")
    public void deleteCategoryById() {
        // GIVEN
        Integer categoryId = 1;

        // WHEN
        BDDMockito.willDoNothing().given(categoryRepositoryImpl).deleteById(categoryId);
        BDDMockito.willDoNothing().given(categoryRepositoryImpl).updateProductCategoryId(categoryId);
        categoryService.deleteCategoryById(categoryId);

        //THEN
        verify(categoryRepositoryImpl, times(1)).deleteById(categoryId);
        verify(categoryRepositoryImpl, times(1)).updateProductCategoryId(categoryId);

    }

    @Test
    @DisplayName("Add category")
    public void addCategory() {
        // GIVEN
        CategoryEntity categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setId(1);
        categoryEntityOne.setName("Category 1");
        categoryEntityOne.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        CategoryDto categoryDtoOne = new CategoryDto();
        categoryDtoOne.setId(1);
        categoryDtoOne.setName("Category dto 1");

        // WHEN
        BDDMockito.given(categoryMapper.toCategoryEntity(categoryDtoOne)).willReturn(categoryEntityOne);
        BDDMockito.given(categoryRepositoryImpl.save(categoryEntityOne)).willReturn(categoryEntityOne);
        BDDMockito.given(categoryMapper.toCategoryDto(categoryEntityOne)).willReturn(categoryDtoOne);
        CategoryDto categoryDtoRes = categoryService.addCategory(categoryDtoOne);

        // THEN
        assertThat(categoryDtoRes).isNotNull();
        assertThat(categoryDtoRes.getId()).isEqualTo(categoryDtoOne.getId());
        assertThat(categoryDtoRes.getName()).isEqualTo(categoryDtoOne.getName());
        verify(categoryRepositoryImpl, times(1)).save(categoryEntityOne);
        verify(categoryMapper, times(1)).toCategoryDto(categoryEntityOne);
        verify(categoryMapper, times(1)).toCategoryEntity(categoryDtoOne);
    }

    @Test
    @DisplayName("Update category")
    public void updateCategory() {
        // GIVEN
        CategoryEntity categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setId(1);
        categoryEntityOne.setName("Category 1");
        categoryEntityOne.setProducts(List.of(new ProductEntity(), new ProductEntity()));

        CategoryDto categoryDtoOne = new CategoryDto();
        categoryDtoOne.setId(1);
        categoryDtoOne.setName("Category dto 1");

        // WHEN
        BDDMockito.given(categoryMapper.toCategoryEntity(categoryDtoOne)).willReturn(categoryEntityOne);
        BDDMockito.given(categoryRepositoryImpl.update(categoryEntityOne)).willReturn(categoryEntityOne);
        BDDMockito.given(categoryMapper.toCategoryDto(categoryEntityOne)).willReturn(categoryDtoOne);
        CategoryDto categoryDtoRes = categoryService.updateCategory(categoryDtoOne);

        // THEN
        assertThat(categoryDtoRes).isNotNull();
        assertThat(categoryDtoRes.getId()).isEqualTo(categoryDtoOne.getId());
        assertThat(categoryDtoRes.getName()).isEqualTo(categoryDtoOne.getName());
        verify(categoryRepositoryImpl, times(1)).update(categoryEntityOne);
        verify(categoryMapper, times(1)).toCategoryDto(categoryEntityOne);
        verify(categoryMapper, times(1)).toCategoryEntity(categoryDtoOne);

    }
}
