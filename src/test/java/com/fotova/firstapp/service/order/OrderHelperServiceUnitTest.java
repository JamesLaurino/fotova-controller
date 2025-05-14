package com.fotova.firstapp.service.order;

import com.fotova.dto.orderProduct.OrderProductDto;
import com.fotova.service.order.helper.OrderHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class OrderHelperServiceUnitTest {
    private OrderHelper orderHelper = new OrderHelper();

    @Test
    @DisplayName("computeBillingTotal")
    public void computeBillingTotal() {
        OrderProductDto orderProductDto = new OrderProductDto();
        orderProductDto.setOrderId(1);
        orderProductDto.setTotal(10.0);
        orderProductDto.setName("name 1");
        orderProductDto.setPrice(5.0);
        orderProductDto.setEmail("email 1");
        orderProductDto.setQuantity(2);
        orderProductDto.setCreationDate("11-01-1991");

        OrderProductDto orderProductDtoTwo = new OrderProductDto();
        orderProductDtoTwo.setOrderId(1);
        orderProductDtoTwo.setTotal(18.0);
        orderProductDtoTwo.setName("name 2");
        orderProductDtoTwo.setPrice(6.0);
        orderProductDtoTwo.setEmail("email 2");
        orderProductDtoTwo.setQuantity(3);
        orderProductDtoTwo.setCreationDate("11-01-1987");

        OrderProductDto orderProductDtoThree = new OrderProductDto();
        orderProductDtoThree.setOrderId(1);
        orderProductDtoThree.setTotal(8.0);
        orderProductDtoThree.setName("name 3");
        orderProductDtoThree.setPrice(4.0);
        orderProductDtoThree.setEmail("email 3");
        orderProductDtoThree.setQuantity(2);
        orderProductDtoThree.setCreationDate("01-10-1967");

        // WHEN
        Double billingTotal = orderHelper
                .computeBillingTotal(List.of(orderProductDto, orderProductDtoTwo, orderProductDtoThree));

        // THEN
        assertThat(billingTotal).isNotNull();
        assertThat(billingTotal).isEqualTo(36.0);
    }


}
