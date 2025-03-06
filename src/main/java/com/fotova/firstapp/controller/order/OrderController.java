package com.fotova.firstapp.controller.order;

import com.fotova.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}