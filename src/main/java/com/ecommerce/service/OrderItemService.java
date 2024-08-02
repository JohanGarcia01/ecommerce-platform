package com.ecommerce.service;

import static com.ecommerce.exception.ErrorCode.ORDER_ITEM_NOT_FOUND;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.request.OrderItemRequest;
import com.ecommerce.repository.OrderItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final ProductService productService;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository
                .findById(id)
                .orElseThrow(() -> new EcommerceException(ORDER_ITEM_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        OrderItem orderItem =
                OrderItem.builder()
                        .product(productService.getProductById(orderItemRequest.getProductId()))
                        .quantity(orderItemRequest.getQuantity())
                        .build();
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(Long id, OrderItemRequest updatedOrderItem) {
        OrderItem existingOrderItem = getOrderItemById(id);
        existingOrderItem.setProduct(productService.getProductById(updatedOrderItem.getProductId()));
        existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
        return orderItemRepository.save(existingOrderItem);
    }

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }
}
