package com.ecommerce.order.exception;

//Kastas när en order inte hittas
public class OrderNotFoundException extends RuntimeException{
    
    public OrderNotFoundException(Long id){
        super("Order not found with the id: " + id);
    }
    
}
