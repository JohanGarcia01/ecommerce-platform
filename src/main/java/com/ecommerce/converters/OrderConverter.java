package com.ecommerce.converters;

import com.ecommerce.model.Order;
import com.ecommerce.model.response.OrderItemResponse;
import com.ecommerce.model.response.OrderResponse;
import java.util.List;

public class OrderConverter {

    public static OrderResponse parseToOrderResponse(Order order) {
        List<OrderItemResponse> orderItemResponseList =
                order.getOrderItems().stream().map(OrderItemConverter::parseToOrderItemResponse).toList();
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .customerAddress(order.getCustomerAddress())
                .orderItems(orderItemResponseList)
                .build();
    }
}
