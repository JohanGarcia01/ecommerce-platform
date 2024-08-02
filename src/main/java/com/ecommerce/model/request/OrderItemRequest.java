package com.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull
    @Schema(description = "Product identifier", example = "1")
    private Long productId;

    @NotNull
    @Schema(description = "Order identifier", example = "1")
    private Long orderId;

    @NotNull
    @Schema(description = "Quantity of products", example = "2")
    private Integer quantity;
}
