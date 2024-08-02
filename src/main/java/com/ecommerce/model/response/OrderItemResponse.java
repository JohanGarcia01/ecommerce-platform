package com.ecommerce.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    @Schema(description = "OrderItem identifier", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Purchased product",
            example = """
                    {
                        "name": "Sample Product",
                        "description": "This is a sample product.",
                        "price": 19.99
                    }""")
    private ProductResponse product;

    @NotNull
    @Schema(description = "Quantity of products", example = "2")
    private Integer quantity;

}
