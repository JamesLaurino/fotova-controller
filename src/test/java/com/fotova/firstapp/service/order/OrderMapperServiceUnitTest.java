package com.fotova.firstapp.service.order;

import com.fotova.dto.order.OrderBasketDto;
import com.fotova.dto.order.OrderClientDto;
import com.fotova.dto.order.OrderDto;
import com.fotova.dto.orderProduct.OrderProductBillingDto;
import com.fotova.dto.orderProduct.OrderProductDto;
import com.fotova.dto.stripe.StripOrderBasket;
import com.fotova.dto.stripe.StripeProductRequest;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.OrderEntity;
import com.fotova.service.order.OrderMapper;
import com.fotova.service.order.helper.OrderHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    @DisplayName("mapToOrderEntity")
    public void mapToOrderEntity() {
        // GIVEN
        OrderClientDto orderClientDto = new OrderClientDto();
        orderClientDto.setId(1);
        orderClientDto.setEmail("email 1");

        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setCreateAt(Instant.now());
        orderDto.setClient(orderClientDto);

        Integer clientId = orderClientDto.getId();

        // WHEN
        OrderEntity orderEntity = orderMapper.mapToOrderEntity(orderDto, clientId);

        // THEN
        assertThat(orderEntity).isNotNull();
        assertThat(orderEntity.getClient().getId()).isEqualTo(clientId);
        assertThat(orderEntity.getClient().getEmail()).isEqualTo(orderClientDto.getEmail());
        assertThat(orderEntity.getCreateAt()).isEqualTo(orderDto.getCreateAt());
    }

    @Test
    @DisplayName("mapToOrderProductBillingDto")
    public void mapToOrderProductBillingDto() {
        // GIVEN
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setOrderId(1);
        orderProductDto.setEmail("email 1");
        orderProductDto.setName("name 1");
        orderProductDto.setPrice(10.0);
        orderProductDto.setQuantity(4);
        orderProductDto.setTotal(55.0);
        orderProductDto.setCreationDate("11-01-1991");

        List<OrderProductDto> orderProductDtos = List.of(orderProductDto);

        // WHEN
        BDDMockito.given(orderHelper.computeBillingTotal(orderProductDtos)).willReturn(55.0);
        OrderProductBillingDto orderProductBillingDto = orderMapper.mapToOrderProductBillingDto(orderProductDtos);

        // THEN
        assertThat(orderProductBillingDto).isNotNull();
        assertThat(orderProductBillingDto.getTotal()).isEqualTo(55.0);
        assertThat(orderProductBillingDto.getOrderId()).isEqualTo(orderProductDto.getOrderId());
        assertThat(orderProductBillingDto.getProductBillingDtoList().get(0).getPrice())
                .isEqualTo(orderProductDto.getPrice());

        assertThat(orderProductBillingDto.getProductBillingDtoList().get(0).getName())
                .isEqualTo(orderProductDto.getName());

    }

    @Test
    @DisplayName("mapOrderBasketWithStripeRequest")
    public void mapOrderBasketWithStripeRequest() {
        // GIVEN
        StripeProductRequest stripeProductRequest = new StripeProductRequest();
        stripeProductRequest.setName("name 1");
        stripeProductRequest.setQuantity(4L);
        stripeProductRequest.setEmail("email 1");
        stripeProductRequest.setAmount(5L);
        stripeProductRequest.setCurrency("EUR");

        StripOrderBasket stripOrderBasket = new StripOrderBasket();
        stripOrderBasket.setQuantity(5);
        stripOrderBasket.setProductId(1);

        StripOrderBasket stripOrderBasketTwo = new StripOrderBasket();
        stripOrderBasketTwo.setQuantity(1);
        stripOrderBasketTwo.setProductId(5);

        stripeProductRequest.setProductBasket(List.of(stripOrderBasket, stripOrderBasketTwo));

        // WHEN
        List<OrderBasketDto> orderBasketDtoList = orderMapper
                .mapOrderBasketWithStripeRequest(stripeProductRequest);

        // THEN
        assertThat(orderBasketDtoList).isNotEmpty();
        assertThat(orderBasketDtoList).hasSize(2);
        assertThat(orderBasketDtoList).isNotNull();

        assertThat(orderBasketDtoList.get(0).getQuantity()).isEqualTo(5);
        assertThat(orderBasketDtoList.get(0).getProductId()).isEqualTo(1);

        assertThat(orderBasketDtoList.get(1).getQuantity()).isEqualTo(1);
        assertThat(orderBasketDtoList.get(1).getProductId()).isEqualTo(5);
    }

}
