package com.ecommerce.product.exception;

    /**
     * Custom exception som används när en produkt inte hittas.
     *
     * I stället för att kasta en generell RuntimeException
     * skapar vi en tydligare exception som beskriver exakt vad som gick fel.
     */
public class ProductNotFoundException extends RuntimeException{
    /**
     * Skapar ett felmeddelande med produktens id.
     *
     * @param id id för produkten som inte hittades
     */
    public ProductNotFoundException(Long id){
        super("Product not found with id: " + id);
    }
}
