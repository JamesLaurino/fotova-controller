package com.fotova.firstapp.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.order.OrderClientDto;
import com.fotova.dto.order.OrderDto;
import com.fotova.dto.orderProduct.OrderProductBillingDto;
import com.fotova.dto.orderProduct.OrderProductDto;
import com.fotova.service.order.OrderService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all order")
    public void getAllOrdersTest() throws Exception {
        // GIVEN
        List<OrderDto> orderDtoList = List.of(new OrderDto(), new OrderDto(), new OrderDto());

        // WHEN
        BDDMockito.given(orderService.getAllOrders()).willReturn(orderDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/orders"));

        // THEN
        verify(orderService, times(1)).getAllOrders();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(orderDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Orders retrieved successfully")));
    }

    @Test
    @DisplayName("Get order by id")
    public void GetOrderByIdTest() throws Exception {
        // GIVEN
        OrderClientDto orderClientDto = new OrderClientDto();
        orderClientDto.setId(1);
        orderClientDto.setEmail("mail1");
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setCreateAt(Instant.now());
        orderDto.setClient(orderClientDto);

        // WHEN
        BDDMockito.given(orderService.getOrderById(orderDto.getId())).willReturn(orderDto);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/order/{orderId}", orderDto.getId()));

        // THEN
        verify(orderService, times(1)).getOrderById(orderDto.getId());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Order retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                    CoreMatchers.is(orderDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(orderClientDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.client.email",
                        CoreMatchers.is(orderClientDto.getEmail())));

    }

    @Test
    @DisplayName("Add an order")
    public void addOrderTest() throws Exception {
        // GIVEN
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setCreateAt(Instant.now());
        orderDto.setClient(new OrderClientDto());
        orderDto.getClient().setId(1);
        orderDto.getClient().setEmail("<EMAIL>");

        // WHEN
        BDDMockito.given(orderService.addOrder(any(OrderDto.class))).willReturn(orderDto);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/order/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderDto)));

        // THEN
        verify(orderService, times(1)).addOrder(any(OrderDto.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Order added successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(orderDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.client.email",
                        CoreMatchers.is(orderDto.getClient().getEmail())));
    }

    @Test
    @DisplayName("Get order product by email")
    public void getOrderProductByEmailTest() throws Exception {
        // GIVEN
        String email = "email";
        Integer orderId = 1;
        List<OrderProductDto> productDtoList = List.of(
                new OrderProductDto(),
                new OrderProductDto(),
                new OrderProductDto()
        );

        // WHEN
        BDDMockito.given(orderService.getOrderProductByEmail(eq(email), eq(orderId))).willReturn(productDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/order-products")
                .param("email", email)
                .param("orderId", orderId.toString())
                .contentType("application/json")
        );

        // THEN
        verify(orderService, times(1)).getOrderProductByEmail(eq(email), eq(orderId));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Order product retrieve successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", CoreMatchers.is(productDtoList.size())));

    }

    @Test
    @DisplayName("Get order product billing by email")
    void GetOrderProductBillingByEmailTest() throws Exception {
        // GIVEN
        String email = "email@example.com";
        Integer orderId = 1;

        // Crée un DTO factice
        OrderProductBillingDto billingDto = new OrderProductBillingDto();
        billingDto.setTotal(100.0); // exemple de champ fictif


        // WHEN
        BDDMockito.given(orderService.getOrderProductBillingByEmail(eq(email), eq(orderId)))
                .willReturn(billingDto);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/order-products/billing")
                .param("email", email)
                .param("orderId", orderId.toString())
                .contentType("application/json")
        );

        // THEN
        verify(orderService, times(1)).getOrderProductBillingByEmail(eq(email), eq(orderId));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Order product retrieve successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.total",
                        CoreMatchers.is(billingDto.getTotal())));
    }

}
