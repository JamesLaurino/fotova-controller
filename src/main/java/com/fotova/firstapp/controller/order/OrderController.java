package com.fotova.firstapp.controller.order;

import com.fotova.dto.comment.CommentDto;
import com.fotova.dto.order.OrderDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("auth/orders")
    public ResponseEntity<Object> getAllOrders() {
        Response<List<OrderDto>> response = Response.<List<OrderDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Orders retrieved successfully")
                .data(orderService.getAllOrders())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/order/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("auth/order/add")
    public ResponseEntity<Object> addOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.addOrder(orderDto));
    }

    @GetMapping("auth/order-products")
    public ResponseEntity<Object> getOrderProductByEmail(@RequestParam String email,@RequestParam Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderProductByEmail(email,orderId));
    }

    @GetMapping("auth/order-products/billing")
    public ResponseEntity<Object> getOrderProductBillingByEmail(@RequestParam String email,@RequestParam Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderProductBillingByEmail(email,orderId));
    }
}