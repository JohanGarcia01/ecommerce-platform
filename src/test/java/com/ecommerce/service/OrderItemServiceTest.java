package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.request.OrderItemRequest;
import com.ecommerce.repository.OrderItemRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

public class OrderItemServiceTest {

    @Mock private OrderItemRepository orderItemRepository;

    @Mock private ProductService productService;

    @InjectMocks private OrderItemService orderItemService;

    private OrderItem orderItem;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product =
                Product.builder()
                        .id(1L)
                        .name("Sample Product")
                        .description("This is a sample product.")
                        .price(19.99)
                        .build();

        orderItem = OrderItem.builder().id(1L).product(product).quantity(1).build();
    }

    @Test
    public void getAllOrderItems() {
        List<OrderItem> orderItems = Arrays.asList(orderItem);

        when(orderItemRepository.findAll()).thenReturn(orderItems);

        List<OrderItem> result = orderItemService.getAllOrderItems();

        assertEquals(orderItems.size(), result.size());
        verify(orderItemRepository, times(1)).findAll();
    }

    @Test
    public void getOrderItemById() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));

        OrderItem result = orderItemService.getOrderItemById(1L);

        assertEquals(orderItem.getId(), result.getId());
        verify(orderItemRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getOrderItemById_NotFound() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            orderItemService.getOrderItemById(1L);
                        });

        assertEquals("ORDER-ITEM-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(orderItemRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createOrderItem() {
        OrderItemRequest orderItemRequest = new OrderItemRequest(1L, 1L, 1);

        when(productService.getProductById(anyLong())).thenReturn(product);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem result = orderItemService.createOrderItem(orderItemRequest);

        assertEquals(orderItem.getId(), result.getId());
        assertEquals(orderItem.getProduct(), result.getProduct());
        assertEquals(orderItem.getQuantity(), result.getQuantity());
        verify(productService, times(1)).getProductById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void updateOrderItem() {
        OrderItemRequest updatedOrderItemRequest = new OrderItemRequest(1L, 1L, 2);

        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem result = orderItemService.updateOrderItem(1L, updatedOrderItemRequest);

        assertEquals(orderItem.getId(), result.getId());
        assertEquals(orderItem.getProduct(), result.getProduct());
        assertEquals(updatedOrderItemRequest.getQuantity(), result.getQuantity());
        verify(orderItemRepository, times(1)).findById(anyLong());
        verify(productService, times(1)).getProductById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void updateOrderItem_NotFound() {
        OrderItemRequest updatedOrderItemRequest = new OrderItemRequest(1L, 1L, 2);

        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            orderItemService.updateOrderItem(1L, updatedOrderItemRequest);
                        });

        assertEquals("ORDER-ITEM-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(orderItemRepository, times(1)).findById(anyLong());
        verify(orderItemRepository, times(0)).save(any(OrderItem.class));
    }

    @Test
    public void deleteOrderItem() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));

        orderItemService.deleteOrderItem(1L);

        verify(orderItemRepository, times(1)).findById(anyLong());
        verify(orderItemRepository, times(1)).delete(any(OrderItem.class));
    }

    @Test
    public void deleteOrderItem_NotFound() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            orderItemService.deleteOrderItem(1L);
                        });

        assertEquals("ORDER-ITEM-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(orderItemRepository, times(1)).findById(anyLong());
        verify(orderItemRepository, times(0)).delete(any(OrderItem.class));
    }
}
