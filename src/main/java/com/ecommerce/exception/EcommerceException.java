package com.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/** This exception is used to handle errors specific to the e-commerce platform. */
@Getter
public class EcommerceException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public EcommerceException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode.getDescription());
        this.errorCode = errorCode.getCode();
        this.httpStatus = httpStatus;
        this.message = errorCode.getDescription();
    }
}
