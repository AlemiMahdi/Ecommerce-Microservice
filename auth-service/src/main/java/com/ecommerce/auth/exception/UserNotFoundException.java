package com.ecommerce.auth.exception;

//Kastas när användare inte hittas
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("User not found with id: " + id);
    }
}
