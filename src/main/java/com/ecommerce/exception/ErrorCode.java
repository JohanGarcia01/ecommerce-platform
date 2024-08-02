package com.ecommerce.exception;

/** Enum for defining error codes and descriptions for the E-Commerce platform. */
public enum ErrorCode {

    // Business errors
    PRODUCT_NOT_FOUND("Product not found."),
    ORDER_NOT_FOUND("Order not found."),
    ORDER_ITEM_NOT_FOUND("Order item not found.");

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String getCode() {
        return this.name().replace("_", "-");
    }

    public String getDescription() {
        return description;
    }
}
