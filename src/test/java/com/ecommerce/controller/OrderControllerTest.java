package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.request.OrderRequest;
import com.ecommerce.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
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

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    private MockMvc mockMvc;

    private Order order;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
        order = Order.builder()
                .id(1L)
                .customerName("Customer 1")
                .customerAddress("Address 1")
                .orderItems(new ArrayList<>())
                .build();
    }

    @Test
    public void getAllOrders() throws Exception {
        Order order2 = new Order(2L, "Customer 2", "Address 2", new ArrayList<>());
        List<Order> orders = Arrays.asList(order, order2);

        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(orders.size()))
                .andExpect(jsonPath("$[0].customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$[1].customerName").value(order2.getCustomerName()));
    }

    @Test
    public void getOrderById() throws Exception {

        when(orderService.getOrderById(anyLong())).thenReturn(order);

        mockMvc.perform(get("/api/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.customerAddress").value(order.getCustomerAddress()));
    }

    @Test
    public void createOrder() throws Exception {

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerName": "Customer 1",
                                    "customerAddress": "Address 1",
                                    "orderItems": []
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.customerAddress").value(order.getCustomerAddress()));
    }

    @Test
    public void updateOrder() throws Exception {

        when(orderService.updateOrder(anyLong(), any(OrderRequest.class))).thenReturn(order);

        mockMvc.perform(put("/api/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "customerName": "Customer 1",
                                    "customerAddress": "Address 1",
                                    "orderItems": []
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()))
                .andExpect(jsonPath("$.customerAddress").value(order.getCustomerAddress()));
    }

    @Test
    public void deleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
