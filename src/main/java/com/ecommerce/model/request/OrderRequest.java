package com.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank
    @Schema(description = "Customer name", example = "Johan Garcia")
    private String customerName;

    @NotBlank
    @Schema(description = "Customer address", example = "123 Main St")
    private String customerAddress;

    @Schema(description = "List of order items identifiers to buy", example = "[1, 2]")
    private List<Long> orderItems;
}
