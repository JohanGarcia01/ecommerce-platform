package com.ecommerce.controller;

import static com.ecommerce.converters.OrderItemConverter.parseToOrderItemResponse;

import com.ecommerce.converters.OrderItemConverter;
import com.ecommerce.model.request.OrderItemRequest;
import com.ecommerce.model.response.OrderItemResponse;
import com.ecommerce.service.OrderItemService;
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
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    @Operation(summary = "Get all order items", description = "Retrieve a list of all order items.")
    @ApiResponse(responseCode = "200", description = "List of order items retrieved successfully")
    public ResponseEntity<List<OrderItemResponse>> getAllOrderItems() {
        List<OrderItemResponse> orderItems =
                orderItemService.getAllOrderItems().stream()
                        .map(OrderItemConverter::parseToOrderItemResponse)
                        .collect(Collectors.toList());
        ;
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order item by ID", description = "Retrieve an order item by its ID.")
    @ApiResponse(responseCode = "201", description = "Order item retrieved successfully")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Long id) {
        OrderItemResponse orderItem = parseToOrderItemResponse(orderItemService.getOrderItemById(id));
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping
    @Operation(
            summary = "Create a new order item",
            description = "Add a new order item to the system.")
    @ApiResponse(responseCode = "201", description = "Order item created successfully")
    public ResponseEntity<OrderItemResponse> createOrderItem(
            @Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderItemResponse orderItem =
                parseToOrderItemResponse(orderItemService.createOrderItem(orderItemRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing order item",
            description = "Update the details of an existing order item.")
    @ApiResponse(responseCode = "200", description = "Order item updated successfully")
    public ResponseEntity<OrderItemResponse> updateOrderItem(
            @PathVariable Long id, @Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderItemResponse updatedOrderItem =
                parseToOrderItemResponse(orderItemService.updateOrderItem(id, orderItemRequest));
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an order item by ID",
            description = "Remove an order item from the system by its ID.")
    @ApiResponse(responseCode = "204", description = "Order item deleted successfully")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
