package com.fotova.firstapp.controller.order;

import com.fotova.dto.order.OrderDto;
import com.fotova.dto.orderProduct.OrderProductBillingDto;
import com.fotova.dto.orderProduct.OrderProductDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Retrieve all the orders")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderDto.class))
            })
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

    @Operation(summary = "Toggle the completeness of an order")
    @ApiResponse(responseCode = "200", description = "Order completeness updated successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content)
    @PutMapping("auth/order/complete/{orderId}")
    public ResponseEntity<Object> toggleCompletedOrder(
            @Parameter(description = "Order identifier - id", required = true, example = "1")
            @PathVariable Integer orderId) {
        Response<OrderDto> response = Response.<OrderDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order completeness updated successfully")
                .data(orderService.toggleCompleted(orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve an order by its id")
    @ApiResponse(responseCode = "200", description = "Order retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content)
    @GetMapping("auth/order/{orderId}")
    public ResponseEntity<Object> getOrderById(
            @Parameter(description = "Order identifier - id", required = true, example = "1")
            @PathVariable Integer orderId) {
        Response<OrderDto> response = Response.<OrderDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order retrieved successfully")
                .data(orderService.getOrderById(orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new order")
    @ApiResponse(responseCode = "200", description = "Order added successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderDto.class))
            })
    @ApiResponse(responseCode = "409", description = "Order already exists",
            content = @Content)
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

    @Operation(summary = "Retrieve order products by email and order id")
    @ApiResponse(responseCode = "200", description = "Order products retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderProductDto.class))
            })
    @GetMapping("auth/order-products")
    public ResponseEntity<Object> getOrderProductByEmail(
            @Parameter(description = "Client email", required = true, example = "exemple@gmail.com")
            @RequestParam String email,
            @Parameter(description = "Order identifier - id", required = true, example = "1")
            @RequestParam Integer orderId) {
        Response<List<OrderProductDto>> response = Response.<List<OrderProductDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order product retrieve successfully")
                .data(orderService.getOrderProductByEmail(email,orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve all orders by email")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderProductDto.class))
            })
    @GetMapping("auth/order-products/{email}")
    public ResponseEntity<Object> getOrdersByEmail(
            @Parameter(description = "Client email", required = true, example = "exemple@gmail.com")
            @PathVariable String email) {
        Response<List<OrderProductDto>> response = Response.<List<OrderProductDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Orders retrieve successfully")
                .data(orderService.getOrderProductsByEmail(email))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve all orders with details")
    @ApiResponse(responseCode = "200", description = "Orders detailed retrieve successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderProductDto.class))
            })
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


    @Operation(summary = "Retrieve order product billing by email and order id")
    @ApiResponse(responseCode = "200", description = "Order product retrieve successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = OrderProductBillingDto.class))
            })
    @GetMapping("auth/order-products/billing")
    public ResponseEntity<Object> getOrderProductBillingByEmail(
            @Parameter(description = "Client email", required = true, example = "exemple@gmail.com")
            @RequestParam String email,
            @Parameter(description = "Order identifier - id", required = true, example = "1")
            @RequestParam Integer orderId) {
        Response<OrderProductBillingDto> response = Response.<OrderProductBillingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Order product retrieve successfully")
                .data(orderService.getOrderProductBillingByEmail(email,orderId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}