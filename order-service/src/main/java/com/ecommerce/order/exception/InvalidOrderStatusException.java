package com.ecommerce.order.exception;

//Kastas när en order inte kan ändra status
//ex. en order som redan är CANCELLED ska inte kunna avbrytas igen
public class InvalidOrderStatusException extends RuntimeException{
    
    public InvalidOrderStatusException(String message){
        super(message);
    }
    
}
