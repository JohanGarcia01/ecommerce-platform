package com.ecommerce.service;

import static com.ecommerce.exception.ErrorCode.PRODUCT_NOT_FOUND;

import com.ecommerce.exception.EcommerceException;
import com.ecommerce.model.Product;
import com.ecommerce.model.request.ProductRequest;
import com.ecommerce.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new EcommerceException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Product createProduct(ProductRequest product) {
        Product productEntity =
                Product.builder()
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build();
        return productRepository.save(productEntity);
    }

    public Product updateProduct(Long id, ProductRequest productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
