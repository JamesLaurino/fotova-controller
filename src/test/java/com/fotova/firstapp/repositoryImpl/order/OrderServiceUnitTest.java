package com.fotova.firstapp.repositoryImpl.order;

import com.fotova.entity.ClientEntity;
import com.fotova.entity.OrderEntity;
import com.fotova.repository.order.OrderRepositoryImpl;
import com.fotova.repository.order.OrderRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
    @Mock
    private OrderRepositoryJpa orderRepositoryJpa;

    @InjectMocks
    private OrderRepositoryImpl orderRepositoryImpl;

    private OrderEntity orderEntityOne = new OrderEntity();
    private OrderEntity orderEntityTwo = new OrderEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        orderEntityOne.setId(1);
        orderEntityOne.setClient(clientEntity);
        orderEntityOne.setCreateAt(Instant.now());

        orderEntityTwo.setId(2);
        orderEntityTwo.setClient(clientEntity);
        orderEntityTwo.setCreateAt(Instant.now());
    }

    @Test
    @DisplayName("Find all orders")
    @Order(1)
    public void givenOrderRepository_whenFindAll_thenReturnList() {

        // GIVEN
        BDDMockito.given(orderRepositoryJpa.findAll()).willReturn(List.of(orderEntityOne, orderEntityTwo));

        //WHEN
        List<OrderEntity> orderEntityList = orderRepositoryImpl.findAll();

        // THEN
        assertThat(orderEntityList).isNotEmpty();
        assertThat(orderEntityList).hasSize(2);
        assertThat(orderEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find order by id")
    @Order(2)
    public void givenOrderRepository_whenFindById_thenReturnOrder() {

        // GIVEN
        BDDMockito.given(orderRepositoryJpa.findById(1)).willReturn(Optional.ofNullable(orderEntityOne));

        // WHEN
        OrderEntity orderEntity = orderRepositoryImpl.findById(1);

        // THEN
        assertThat(orderEntity).isNotNull();
        assertThat(orderEntity.getId()).isEqualTo(1);
        assertThat(orderEntity.getClient().getId()).isEqualTo(1);
        assertThat(orderEntity.getCreateAt()).isNotNull();
    }
}

