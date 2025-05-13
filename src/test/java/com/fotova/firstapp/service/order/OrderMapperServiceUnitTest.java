package com.fotova.firstapp.service.order;

import com.fotova.dto.order.OrderDto;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.OrderEntity;
import com.fotova.service.order.OrderMapper;
import com.fotova.service.order.helper.OrderHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderMapperServiceUnitTest {

    @Mock
    private OrderHelper orderHelper;

    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    @DisplayName("mapToOrderDtoList")
    public void mapToOrderDtoList() {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("email 1");
        clientEntity.setUsername("username 1");
        clientEntity.setPassword("<PASSWORD> 1");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setCreateAt(Instant.now());
        orderEntity.setClient(clientEntity);

        OrderEntity orderEntityTwo = new OrderEntity();
        orderEntity.setId(2);
        orderEntity.setCreateAt(Instant.now());

        List<OrderEntity> orderEntityList = List.of(orderEntity, orderEntityTwo);

        // WHEN
        List<OrderDto> orderDtoList = orderMapper.mapToOrderDtoList(orderEntityList);

        // THEN
        assertThat(orderDtoList).isNotEmpty();
        assertThat(orderDtoList).hasSize(2);
        assertThat(orderDtoList).isNotNull();

        assertThat(orderDtoList.get(0).getClient()).isNotNull();
        assertThat(orderDtoList.get(0).getClient().getEmail()).isEqualTo("email 1");
        assertThat(orderDtoList.get(0).getId()).isEqualTo(orderEntity.getId());

        assertThat(orderDtoList.get(1).getClient()).isNull();
        assertThat(orderDtoList.get(1).getId()).isEqualTo(orderEntityTwo.getId());
    }

}
