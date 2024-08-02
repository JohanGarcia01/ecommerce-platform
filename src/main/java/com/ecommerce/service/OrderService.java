package com.ecommerce.service;

import static com.ecommerce.exception.ErrorCode.ORDER_NOT_FOUND;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.request.OrderRequest;
import com.ecommerce.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemService orderItemService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EcommerceException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Order createOrder(OrderRequest orderRequest) {
        List<OrderItem> orderItems =
                orderRequest.getOrderItems().stream().map(orderItemService::getOrderItemById).toList();
        Order order =
                Order.builder()
                        .customerAddress(orderRequest.getCustomerAddress())
                        .customerName(orderRequest.getCustomerName())
                        .orderItems(orderItems)
                        .build();
        Order orderSaved = orderRepository.save(order);
        orderItems.forEach(orderItem -> orderItem.setOrder(orderSaved));
        orderItems.forEach(orderItemService::save);
        return orderSaved;
    }

    public Order updateOrder(Long id, OrderRequest updatedOrder) {
        Order existingOrder = getOrderById(id);
        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setCustomerAddress(updatedOrder.getCustomerAddress());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}
