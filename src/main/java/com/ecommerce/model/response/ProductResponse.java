package com.ecommerce.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    @Schema(description = "Product identifier", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the product", example = "Sample Product")
    private String name;

    @Schema(description = "Description of the product", example = "Description Product")
    private String description;

    @NotNull
    @Schema(description = "Price of the product", example = "19.99")
    private Double price;
}
