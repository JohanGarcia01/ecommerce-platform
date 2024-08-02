package com.ecommerce.model.response;

import com.ecommerce.model.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    @Schema(description = "Order identifier", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Customer's name", example = "Johan Garcia")
    private String customerName;

    @NotBlank
    @Schema(description = "Customer's address", example = "123 Main St")
    private String customerAddress;

    @Schema(description = "List of order items identifiers to buy",
            example =
                    """
                            [
                                      {
                                        "productId": 1,
                                        "quantity": 2,
                                        "price": 19.99
                                      },
                                      {
                                        "productId": 2,
                                        "quantity": 1,
                                        "price": 29.99
                                      }
                                    ]""")
    private List<OrderItemResponse> orderItems;

}
