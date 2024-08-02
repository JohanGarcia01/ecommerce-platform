package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.Product;
import com.ecommerce.model.request.ProductRequest;
import com.ecommerce.repository.ProductRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

public class ProductServiceTest {

    @Mock private ProductRepository productRepository;

    @InjectMocks private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product =
                Product.builder()
                        .id(1L)
                        .name("Sample Product")
                        .description("This is a sample product.")
                        .price(19.99)
                        .build();
    }

    @Test
    public void getAllProducts() {
        List<Product> products = Arrays.asList(product);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(products.size(), result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals(product.getId(), result.getId());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getProductById_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            productService.getProductById(1L);
                        });

        assertEquals("PRODUCT-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createProduct() {
        ProductRequest productRequest =
                new ProductRequest("New Product", "This is a new product.", 29.99);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(productRequest);

        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getPrice(), result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void updateProduct() {
        ProductRequest updatedProductRequest =
                new ProductRequest("Updated Product", "This is an updated product.", 39.99);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(1L, updatedProductRequest);

        assertEquals(product.getId(), result.getId());
        assertEquals(updatedProductRequest.getName(), result.getName());
        assertEquals(updatedProductRequest.getDescription(), result.getDescription());
        assertEquals(updatedProductRequest.getPrice(), result.getPrice());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void updateProduct_NotFound() {
        ProductRequest updatedProductRequest =
                new ProductRequest("Updated Product", "This is an updated product.", 39.99);

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        EcommerceException exception =
                assertThrows(
                        EcommerceException.class,
                        () -> {
                            productService.updateProduct(1L, updatedProductRequest);
                        });

        assertEquals("PRODUCT-NOT-FOUND", exception.getErrorCode());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    public void deleteProduct() {
        doNothing().when(productRepository).deleteById(anyLong());

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(anyLong());
    }
}
