package com.ecommerce.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Global felhantering för hela product-service.
 *
 * @RestControllerAdvice gör att denna klass kan fånga exceptions
 * från controllers och returnera tydliga JSON-svar.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Hanterar fall där en produkt inte hittas.
     * Returnerar HTTP 404 Not Found.
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(
            ProductNotFoundException exception, HttpServletRequest request
    ){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.name())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    /**
     * Hanterar valideringsfel från @Valid.
     *
     * Exempel:
     * - tomt produktnamn
     * - pris saknas
     * - pris är 0 eller negativt
     *
     * Returnerar HTTP 400 Bad Request.
     */
    

}
