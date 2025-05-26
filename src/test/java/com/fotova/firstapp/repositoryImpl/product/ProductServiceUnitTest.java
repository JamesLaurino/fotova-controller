package com.fotova.firstapp.repositoryImpl.product;

import com.fotova.entity.ProductEntity;
import com.fotova.repository.product.ProductRepositoryImpl;
import com.fotova.repository.product.ProductRepositoryJpa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
public class ProductServiceUnitTest {
    @Mock
    private ProductRepositoryJpa productRepositoryJpa;

    @InjectMocks
    private ProductRepositoryImpl productRepositoryImpl;

    @Mock
    private EntityManager entityManager;

    private ProductEntity productEntityOne = new ProductEntity();
    private ProductEntity productEntityTwo = new ProductEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        productEntityOne.setId(1);
        productEntityOne.setName("Product 1");
        productEntityOne.setUrl("url 1");
        productEntityOne.setPrice(15.0);
        productEntityOne.setQuantity(4);

        productEntityTwo.setId(2);
        productEntityTwo.setName("Product 2");
        productEntityTwo.setUrl("url 2");
        productEntityTwo.setPrice(15.0);
        productEntityTwo.setQuantity(4);

    }

    @Test
    @DisplayName("Find all product")
    @Order(1)
    public void givenProductRepository_whenFindAll_thenReturnList() {

        // GIVEN
        BDDMockito.given(productRepositoryJpa.findAll()).willReturn(List.of(productEntityOne, productEntityTwo));

        //WHEN
        List<ProductEntity> productEntityList = productRepositoryImpl.findAll();

        // THEN
        assertThat(productEntityList).isNotEmpty();
        assertThat(productEntityList).hasSize(2);
        assertThat(productEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find product by id")
    @Order(2)
    public void givenProductRepository_whenFindById_thenReturnComment() {

        // GIVEN
        BDDMockito.given(productRepositoryJpa.findById(1)).willReturn(Optional.ofNullable(productEntityOne));

        // WHEN
        ProductEntity productEntity = productRepositoryImpl.findById(1);

        // THEN
        assertThat(productEntity).isNotNull();
        assertThat(productEntity.getId()).isEqualTo(1);
        assertThat(productEntity.getName()).isEqualTo("Product 1");
        assertThat(productEntity.getPrice()).isEqualTo(15.0);
        assertThat(productEntity.getQuantity()).isEqualTo(4);
        assertThat(productEntity.getUrl()).isEqualTo("url 1");

    }

    @Test
    @DisplayName("Delete product by id")
    @Order(3)
    public void givenDeleteAllProduct_whenDeleteById_thenSuccess() {

        // WHEN
        BDDMockito.willDoNothing().given(productRepositoryJpa).deleteById(1);
        productRepositoryImpl.deleteById(1);

        // THEN : vérifier que la méthode est utilisée avec le bon paramètre
        verify(productRepositoryJpa, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Update product")
    @Order(4)
    public void givenUpdateProduct_whenUpdateById_thenSuccess() {

        // GIVEN
        ProductEntity productEntityUpdate = new ProductEntity();
        productEntityUpdate.setId(1);
        productEntityUpdate.setName("Product 1 update");
        productEntityUpdate.setUrl("url 1 update");
        productEntityUpdate.setPrice(15.0);
        productEntityUpdate.setQuantity(4);

        Mockito.when(productRepositoryJpa.save(productEntityOne)).thenReturn(productEntityUpdate);

        // WHEN
        ProductEntity result = productRepositoryImpl.update(productEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Product 1 update");
        assertThat(result.getUrl()).isEqualTo("url 1 update");
        verify(productRepositoryJpa, times(1)).save(productEntityOne);
    }
}
