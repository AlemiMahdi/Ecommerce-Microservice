package com.ecommerce.auth.exception;

//Kastas när login misslyckas
public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid username or password");
    }
}
