package com.ecommerce.controller;

import static com.ecommerce.converters.ProductConverter.parseToProductResponse;

import com.ecommerce.converters.ProductConverter;
import com.ecommerce.model.request.ProductRequest;
import com.ecommerce.model.response.ProductResponse;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products.")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products =
                productService.getAllProducts().stream()
                        .map(ProductConverter::parseToProductResponse)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID", description = "Retrieve a product by its ID.")
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = parseToProductResponse(productService.getProductById(id));
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Add a new product to the catalog.")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest product) {
        ProductResponse productCreated = parseToProductResponse(productService.createProduct(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing product",
            description = "Update the details of an existing product.")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductRequest product) {
        ProductResponse updatedProduct =
                parseToProductResponse(productService.updateProduct(id, product));
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product by ID",
            description = "Remove a product from the catalog by its ID.")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
