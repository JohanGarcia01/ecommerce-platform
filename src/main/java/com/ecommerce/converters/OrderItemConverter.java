package com.ecommerce.converters;

import static com.ecommerce.converters.ProductConverter.parseToProductResponse;

import com.ecommerce.model.OrderItem;
import com.ecommerce.model.response.OrderItemResponse;
import com.ecommerce.model.response.ProductResponse;

public class OrderItemConverter {

    public static OrderItemResponse parseToOrderItemResponse(OrderItem orderItem) {
        ProductResponse productResponse = parseToProductResponse(orderItem.getProduct());
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .product(productResponse)
                .quantity(orderItem.getQuantity())
                .build();
    }
}
