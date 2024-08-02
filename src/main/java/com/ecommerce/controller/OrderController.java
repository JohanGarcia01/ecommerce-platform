package com.ecommerce.controller;

import static com.ecommerce.converters.OrderConverter.parseToOrderResponse;

import com.ecommerce.converters.OrderConverter;
import com.ecommerce.model.request.OrderRequest;
import com.ecommerce.model.response.OrderResponse;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders.")
    @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders =
                orderService.getAllOrders().stream()
                        .map(OrderConverter::parseToOrderResponse)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID", description = "Retrieve an order by its ID.")
    @ApiResponse(responseCode = "201", description = "Order retrieved successfully")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderResponse order = parseToOrderResponse(orderService.getOrderById(id));
        return ResponseEntity.ok(order);
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Add a new order to the system.")
    @ApiResponse(responseCode = "201", description = "Order created successfully")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse order = parseToOrderResponse(orderService.createOrder(orderRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing order",
            description = "Update the details of an existing order.")
    @ApiResponse(responseCode = "200", description = "Order updated successfully")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable Long id, @Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse updatedOrder = parseToOrderResponse(orderService.updateOrder(id, orderRequest));
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an order by ID",
            description = "Remove an order from the system by its ID.")
    @ApiResponse(responseCode = "204", description = "Order deleted successfully")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
