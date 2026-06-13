package com.ecommerce.auth.exception;

//Kastas när någon försöker registrerar ett username som redan finns
public class UsernameAlreadyExistsException extends RuntimeException{
    
    public UsernameAlreadyExistsException(String username){
        super("Username already exists: " + username);
    }
}
