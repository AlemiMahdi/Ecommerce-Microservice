package com.ecommerce.order.exception;

//Kastas när order-service inte kan kommunicera med product-service
public class ProductserviceException extends RuntimeException{
    
    public ProductserviceException(String message){
        super(message);
    }
}
