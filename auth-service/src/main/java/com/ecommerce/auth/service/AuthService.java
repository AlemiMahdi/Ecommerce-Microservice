package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.AuthResponse;
import com.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.auth.dto.RegisterRequest;

//Interface for autentisering. Här definierar vi vad auth-service ska kunna göra
public interface AuthService {
    
    //Registrerar en ny användare
    AuthResponse register(RegisterRequest request);

    //Logga in en befintlig användare
    AuthResponse login(LoginRequest request);

}
