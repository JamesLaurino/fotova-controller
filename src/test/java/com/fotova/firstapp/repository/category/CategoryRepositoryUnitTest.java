package com.fotova.firstapp.repository.category;

import com.fotova.entity.CategoryEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.repository.category.CategoryRepositoryJpa;
import com.fotova.repository.product.ProductRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryRepositoryUnitTest {
    @Autowired
    private ProductRepositoryJpa productRepositoryJpa;;
    @Autowired
    private CategoryRepositoryJpa categoryRepositoryJpa;

    private CategoryEntity categoryEntityOne;

    @BeforeEach
    public void init() {
        categoryRepositoryJpa.deleteAll();
        productRepositoryJpa.deleteAll();

        // GIVEN
        categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setName("category 1");

        CategoryEntity categoryEntityTwo = new CategoryEntity();
        categoryEntityTwo.setName("category 2");

        categoryRepositoryJpa.save(categoryEntityOne);
        categoryRepositoryJpa.save(categoryEntityTwo);

        ProductEntity productEntityOne = new ProductEntity();
        productEntityOne.setName("product 1");
        productEntityOne.setPrice(100.0);
        productEntityOne.setQuantity(4);
        productEntityOne.setUrl("url 1");
        productEntityOne.setCategory(categoryEntityOne);

        ProductEntity productEntityTwo = new ProductEntity();
        productEntityTwo.setName("product 2");
        productEntityTwo.setPrice(100.0);
        productEntityTwo.setQuantity(4);
        productEntityTwo.setUrl("url 2");
        productEntityTwo.setCategory(categoryEntityTwo);

        productRepositoryJpa.save(productEntityOne);
        productRepositoryJpa.save(productEntityTwo);

    }

    @Test
    @DisplayName("Product category is null after updateProductCategoryId")
    @Order(1)
    public void givenCategoryRepository_whenUpdateProductCategoryId_thenReturnList() {

        // WHEN
        categoryRepositoryJpa.updateProductCategoryId(categoryEntityOne.getId());
        List<ProductEntity> productEntityList = productRepositoryJpa.findAll();

        // THEN
        assertThat(productEntityList).isNotEmpty();
        assertThat(productEntityList).hasSize(2);
        assertThat(productEntityList).isNotNull();

        productEntityList.forEach((product) -> {
            if( product.getCategory() != null)
                assertThat(product.getCategory().getId()).isNotEqualTo(categoryEntityOne.getId());
        });
    }
}
