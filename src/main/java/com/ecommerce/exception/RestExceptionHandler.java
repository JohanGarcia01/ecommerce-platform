package com.ecommerce.exception;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EcommerceException.class})
    @ResponseBody
    protected ResponseEntity<ApiError> handleEcommerceException(EcommerceException exception) {
        ApiError apiError =
                ApiError.builder()
                        .withErrorCode(exception.getErrorCode())
                        .withMessage(exception.getMessage())
                        .withTimestamp(LocalDateTime.now())
                        .build();
        return ResponseEntity.status(exception.getHttpStatus()).body(apiError);
    }
}
