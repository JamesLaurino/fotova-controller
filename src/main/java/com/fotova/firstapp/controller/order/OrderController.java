package com.fotova.firstapp.controller.order;

import com.fotova.dto.comment.CommentDto;
import com.fotova.dto.order.OrderDto;
import com.fotova.dto.orderProduct.OrderProductBillingDto;
import com.fotova.dto.orderProduct.OrderProductDto;
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

    @PutMapping("auth/order/complete/{orderId}")
    public ResponseEntity<Object> toggleCompletedOrder(@PathVariable Integer orderId) {
        Response<OrderDto> response = Response.<OrderDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order completeness updated successfully")
                .data(orderService.toggleCompleted(orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/order/{orderId}")
    public ResponseEntity<Object> getOrderById(@PathVariable Integer orderId) {
        Response<OrderDto> response = Response.<OrderDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order retrieved successfully")
                .data(orderService.getOrderById(orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/order/add")
    public ResponseEntity<Object> addOrder(@RequestBody OrderDto orderDto) {
        Response<OrderDto> response = Response.<OrderDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order added successfully")
                .data(orderService.addOrder(orderDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/order-products")
    public ResponseEntity<Object> getOrderProductByEmail(@RequestParam String email,@RequestParam Integer orderId) {
        Response<List<OrderProductDto>> response = Response.<List<OrderProductDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order product retrieve successfully")
                .data(orderService.getOrderProductByEmail(email,orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/order-products/{email}")
    public ResponseEntity<Object> getOrdersByEmail(@PathVariable String email) {
        Response<List<OrderProductDto>> response = Response.<List<OrderProductDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Orders retrieve successfully")
                .data(orderService.getOrderProductsByEmail(email))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/order-products/detailed")
    public ResponseEntity<Object> getOrdersDetailed() {
        Response<List<OrderProductDto>> response = Response.<List<OrderProductDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Orders detailed retrieve successfully")
                .data(orderService.getOrdersDetailed())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("auth/order-products/billing")
    public ResponseEntity<Object> getOrderProductBillingByEmail(@RequestParam String email,@RequestParam Integer orderId) {
        Response<OrderProductBillingDto> response = Response.<OrderProductBillingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order product retrieve successfully")
                .data(orderService.getOrderProductBillingByEmail(email,orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}