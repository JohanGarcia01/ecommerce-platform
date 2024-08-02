package com.ecommerce.controller;

import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.request.OrderItemRequest;
import com.ecommerce.service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTest {

    @MockBean
    private OrderItemService orderItemService;

    private MockMvc mockMvc;

    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderItemController(orderItemService)).build();
        Product product = Product.builder()
                .name("Name")
                .price(19.99)
                .description("Description")
                .build();
        orderItem = OrderItem.builder()
                .id(1L)
                .product(product)
                .quantity(3)
                .build();
    }

    @Test
    public void getAllOrderItems() throws Exception {
        List<OrderItem> orderItems = Collections.singletonList(orderItem);

        when(orderItemService.getAllOrderItems()).thenReturn(orderItems);

        mockMvc.perform(get("/api/order-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(orderItems.size()))
                .andExpect(jsonPath("$[0].quantity").value(orderItem.getQuantity()));
    }

    @Test
    public void getOrderItemById() throws Exception {

        when(orderItemService.getOrderItemById(anyLong())).thenReturn(orderItem);

        mockMvc.perform(get("/api/order-items/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(orderItem.getQuantity()));
    }

    @Test
    public void createOrderItem() throws Exception {

        when(orderItemService.createOrderItem(any(OrderItemRequest.class))).thenReturn(orderItem);

        mockMvc.perform(post("/api/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productId": 1,
                                    "quantity": 2
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(orderItem.getQuantity()));
    }

    @Test
    public void updateOrderItem() throws Exception {

        when(orderItemService.updateOrderItem(anyLong(), any(OrderItemRequest.class))).thenReturn(orderItem);

        mockMvc.perform(put("/api/order-items/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "productId": 1,
                                    "quantity": 3
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(orderItem.getQuantity()));
    }

    @Test
    public void deleteOrderItem() throws Exception {
        mockMvc.perform(delete("/api/order-items/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
