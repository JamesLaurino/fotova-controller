package com.fotova.firstapp.repository.product;

import com.fotova.entity.ImageEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.repository.image.ImageRepositoryJpa;
import com.fotova.repository.product.ProductRepositoryJpa;
import com.fotova.service.html.authentication.AuthHtmlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepositoryJpa productRepositoryJpa;

    @Autowired
    private ImageRepositoryJpa imageRepositoryJpa;

    @MockitoBean
    private AuthHtmlService authHtmlService;

    ProductEntity productEntity = new ProductEntity();

    @BeforeEach
    public void init() {
        imageRepositoryJpa.deleteAll();
        productRepositoryJpa.deleteAll();

        productEntity = new ProductEntity();
        productEntity.setName("product 1");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(4);
        productEntity.setUrl("url 1");

        productRepositoryJpa.save(productEntity);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setPath("path1");
        imageEntity.setProduct(productEntity);

        ImageEntity imageEntityTwo = new ImageEntity();
        imageEntityTwo.setPath("path2");
        imageEntityTwo.setProduct(productEntity);

        ImageEntity imageEntityThree = new ImageEntity();
        imageEntityThree.setPath("path3");
        imageEntityThree.setProduct(productEntity);

        imageRepositoryJpa.save(imageEntity);
        imageRepositoryJpa.save(imageEntityTwo);
        imageRepositoryJpa.save(imageEntityThree);
        productEntity.setImageEntity(List.of(imageEntity, imageEntityTwo, imageEntityThree));
        productRepositoryJpa.save(productEntity);

    }

    @Test
    @DisplayName("Get Product images")
    public void getProductImages() {
        // WHEN
        List<Object[]> objects = productRepositoryJpa.getProductImages(productEntity.getId());

        // THEN
        assertThat(objects).isNotEmpty();
        assertThat(objects).hasSize(3);

    }
}
