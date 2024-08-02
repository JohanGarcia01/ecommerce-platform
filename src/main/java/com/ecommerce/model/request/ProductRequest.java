package com.ecommerce.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank
    @Schema(description = "Name of the product", example = "Sample Product")
    private String name;

    @Schema(description = "Description of the product", example = "Description Product")
    private String description;

    @NotNull
    @Schema(description = "Price of the product", example = "19.99")
    private Double price;
}
