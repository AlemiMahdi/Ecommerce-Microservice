package com.ecommerce.auth.service;

import java.util.List;

import com.ecommerce.auth.dto.UserResponse;

//User interface for användarrelaterade funktioner
public interface UserService {

    //Hämtar alla användare
    List<UserResponse> getAllUsers();

    //Hämtar en användare med id
    UserResponse getUserById(Long id);
}
