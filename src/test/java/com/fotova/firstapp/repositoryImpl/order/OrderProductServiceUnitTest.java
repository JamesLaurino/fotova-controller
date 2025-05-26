package com.fotova.firstapp.repositoryImpl.order;

import com.fotova.entity.OrderEntity;
import com.fotova.entity.OrderProductEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.repository.order.OrderProductRepositoryImpl;
import com.fotova.repository.order.OrderProductRepositoryJpa;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderProductServiceUnitTest {
    @Mock
    private OrderProductRepositoryJpa orderProductRepositoryJpa;

    @InjectMocks
    private OrderProductRepositoryImpl orderProductRepositoryImpl;

    private OrderProductEntity orderProductEntityOne = new OrderProductEntity();
    private OrderProductEntity orderProductEntityTwo = new OrderProductEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Product 1");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(10);
        productEntity.setUrl("url 1");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);

        orderProductEntityOne.setId(1);
        orderProductEntityOne.setProduct(productEntity);
        orderProductEntityOne.setOrder(orderEntity);
        orderProductEntityOne.setQuantityProduct(4);

        orderProductEntityTwo.setId(2);
        orderProductEntityTwo.setProduct(productEntity);
        orderProductEntityTwo.setOrder(orderEntity);
        orderProductEntityTwo.setQuantityProduct(4);

    }

    @Test
    @DisplayName("Find all order product")
    @Order(1)
    public void givenOrderProductRepository_whenFindAll_thenReturnList() {

        // GIVEN
        BDDMockito.given(orderProductRepositoryJpa.findAll()).willReturn(List.of(orderProductEntityOne, orderProductEntityTwo));

        //WHEN
        List<OrderProductEntity> orderProductEntityList = orderProductRepositoryImpl.findAll();

        // THEN
        assertThat(orderProductEntityList).isNotEmpty();
        assertThat(orderProductEntityList).hasSize(2);
        assertThat(orderProductEntityList).isNotNull();
        verify(orderProductRepositoryJpa, times(1)).findAll();
    }

    @Test
    @DisplayName("Update orderProduct")
    @Order(2)
    public void givenUpdateOrderProduct_whenUpdateById_thenSuccess() {

        // GIVEN
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(2);
        productEntity.setName("Product 2");
        productEntity.setPrice(10.0);
        productEntity.setQuantity(1);
        productEntity.setUrl("url 2");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(2);

        OrderProductEntity orderProductEntityUpdate = new OrderProductEntity();
        orderProductEntityUpdate.setId(1);
        orderProductEntityUpdate.setProduct(productEntity);
        orderProductEntityUpdate.setOrder(orderEntity);
        orderProductEntityUpdate.setQuantityProduct(10);


        Mockito.when(orderProductRepositoryJpa.save(orderProductEntityOne)).thenReturn(orderProductEntityUpdate);

        // WHEN
        OrderProductEntity result = orderProductRepositoryImpl.save(orderProductEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getQuantityProduct()).isEqualTo(10);
        assertThat(result.getOrder().getId()).isEqualTo(2);
        assertThat(result.getProduct().getId()).isEqualTo(2);
        assertThat(result.getProduct().getName()).isEqualTo("Product 2");
        verify(orderProductRepositoryJpa, times(1)).save(orderProductEntityOne);
    }

}
