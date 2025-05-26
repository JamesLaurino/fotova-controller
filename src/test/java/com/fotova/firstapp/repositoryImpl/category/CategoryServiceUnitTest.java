package com.fotova.firstapp.repositoryImpl.category;

import com.fotova.entity.CategoryEntity;
import com.fotova.repository.category.CategoryRepositoryImpl;
import com.fotova.repository.category.CategoryRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryServiceUnitTest {

    @Mock
    private CategoryRepositoryJpa categoryRepositoryJpa;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepositoryImpl;

    private CategoryEntity categoryEntityOne = new CategoryEntity();
    private CategoryEntity categoryEntityTwo = new CategoryEntity();

    @BeforeEach
    public void init() {
        // GIVEN
        categoryEntityOne.setId(1);
        categoryEntityOne.setName("Category 1");

        categoryEntityTwo.setId(2);
        categoryEntityTwo.setName("Category 2");
    }

    @Test
    @DisplayName("Find all category")
    @Order(1)
    public void givenCategoryRepository_whenFindAll_thenReturnList() {
        // GIVEN
        BDDMockito.given(categoryRepositoryJpa.findAll()).willReturn(List.of(categoryEntityOne, categoryEntityTwo));

        //WHEN
        List<CategoryEntity> categoryEntityList = categoryRepositoryImpl.findAll();

        // THEN
        assertThat(categoryEntityList).isNotEmpty();
        assertThat(categoryEntityList).hasSize(2);
        assertThat(categoryEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find category by id")
    @Order(2)
    public void givenCategoryRepository_whenFindById_thenReturnCategory() {

        // GIVEN
        BDDMockito.given(categoryRepositoryJpa.findById(2)).willReturn(Optional.ofNullable(categoryEntityTwo));

        // WHEN
        CategoryEntity categoryEntity = categoryRepositoryImpl.findById(2);

        // THEN
        assertThat(categoryEntity).isNotNull();
        assertThat(categoryEntity.getId()).isEqualTo(2);
        assertThat(categoryEntity.getName()).isEqualTo("Category 2");

    }

    @Test
    @DisplayName("Delete category by id")
    @Order(3)
    public void givenDeleteAllCategory_whenDeleteById_thenSuccess() {
        // WHEN
        BDDMockito.willDoNothing().given(categoryRepositoryJpa).deleteById(2);
        categoryRepositoryImpl.deleteById(2);

        // THEN : vérifier que la méthode est utilisée avec le bon paramètre
        verify(categoryRepositoryJpa, times(1)).deleteById(2);
    }

    @Test
    @DisplayName("Update category")
    @Order(4)
    public void givenUpdateComment_whenUpdateById_thenSuccess() {

        // GIVEN
        CategoryEntity updateCategory = new CategoryEntity();
        updateCategory.setId(1);
        updateCategory.setName("Updated");

        Mockito.when(categoryRepositoryJpa.save(categoryEntityOne)).thenReturn(updateCategory);

        // WHEN
        CategoryEntity result = categoryRepositoryImpl.update(categoryEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated");
        verify(categoryRepositoryJpa, times(1)).save(categoryEntityOne);
    }

    @Test
    @DisplayName("update product category id")
    @Order(5)
    public void given_whenProductId_thenCategoryIsNull() {

        // WHEN
        BDDMockito.willDoNothing().given(categoryRepositoryJpa)
                .updateProductCategoryId(categoryEntityOne.getId());

        categoryRepositoryImpl.updateProductCategoryId(categoryEntityOne.getId());


        // THEN
        verify(categoryRepositoryJpa, times(1))
                .updateProductCategoryId(categoryEntityOne.getId());
    }
}
