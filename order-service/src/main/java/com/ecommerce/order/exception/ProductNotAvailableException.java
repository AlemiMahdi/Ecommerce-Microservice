package com.ecommerce.order.exception;

/**
 * Kastas när en produkt inte kan användas i en order.
 *
 * Exempel:
 * Produkten finns inte i product-service.
 */
public class ProductNotAvailableException extends RuntimeException{
   
    public ProductNotAvailableException(Long productId){
        super("Product not available with id: " + productId);
    }
}
