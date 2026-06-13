package com.ecommerce.auth.exception;

//Kastas när någon försöker registrera en email osm redan finns
public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super("Email already exists" + email);
    }
}
