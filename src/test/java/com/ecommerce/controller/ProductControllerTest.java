package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.model.request.ProductRequest;
import com.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private MockMvc mockMvc;

    private Product product;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
        product = new Product(1L, "Product 1", "Description 1", 100.0);
    }

    @Test
    public void getAllProducts() throws Exception {
        Product product2 = new Product(2L, "Product 2", "Description 2", 200.0);
        List<Product> products = Arrays.asList(product, product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(products.size()))
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()));
    }

    @Test
    public void getProductById() throws Exception {

        when(productService.getProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void createProduct() throws Exception {

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Product 1",
                                    "description": "Description 1",
                                    "price": 100.0
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void updateProduct() throws Exception {

        when(productService.updateProduct(anyLong(), any(ProductRequest.class))).thenReturn(product);

        mockMvc.perform(put("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Product Updated",
                                    "description": "Description Updated",
                                    "price": 150.0
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
