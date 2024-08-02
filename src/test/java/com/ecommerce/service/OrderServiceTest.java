package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.request.OrderRequest;
import com.ecommerce.repository.OrderRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

public class OrderServiceTest {

    @Mock private OrderRepository orderRepository;

    @Mock private OrderItemService orderItemService;

    @InjectMocks private OrderService orderService;

    private Order order;
    private OrderRequest orderRequest;
    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        orderItem = OrderItem.builder().id(1L).quantity(2).build();

        order =
                Order.builder()
                        .id(1L)
                        .customerName("Johan Garcia")
                        .customerAddress("123 Main St")
                        .orderItems(Collections.singletonList(orderItem))
                        .build();

        orderRequest = new OrderRequest("Johan Garcia", "123 Main St", List.of(1L));
    }

    @Test
    public void getAllOrders() {
        List<Order> orders = Arrays.asList(order);

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orders.size(), result.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void getOrderById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getCustomerName(), result.getCustomerName());
        assertEquals(order.getCustomerAddress(), result.getCustomerAddress());
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getOrderById_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            orderService.getOrderById(1L);
                        });

        assertEquals("ORDER-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createOrder() {
        when(orderItemService.getOrderItemById(anyLong())).thenReturn(orderItem);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(orderRequest);

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getCustomerName(), result.getCustomerName());
        assertEquals(order.getCustomerAddress(), result.getCustomerAddress());
        assertEquals(order.getOrderItems(), result.getOrderItems());
        verify(orderItemService, times(1)).getOrderItemById(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void updateOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrder(1L, orderRequest);

        assertEquals(order.getId(), result.getId());
        assertEquals(orderRequest.getCustomerName(), result.getCustomerName());
        assertEquals(orderRequest.getCustomerAddress(), result.getCustomerAddress());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void updateOrder_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            orderService.updateOrder(1L, orderRequest);
                        });

        assertEquals("ORDER-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    public void deleteOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(any(Order.class));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).findById(anyLong());
        verify(orderRepository, times(1)).delete(any(Order.class));
    }
}
