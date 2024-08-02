package com.ecommerce.converters;

import com.ecommerce.model.Product;
import com.ecommerce.model.response.ProductResponse;

public class ProductConverter {

    public static ProductResponse parseToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
