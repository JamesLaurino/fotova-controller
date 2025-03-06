package com.fotova.firstapp.controller.order;

import com.fotova.dto.order.OrderDto;
import com.fotova.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("auth/orders")
    public ResponseEntity<Object> getAllComments() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("auth/order/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("auth/order/add")
    public ResponseEntity<Object> addOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.addOrder(orderDto));
    }
}