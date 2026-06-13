package com.ecommerce.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.auth.dto.UserResponse;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.exception.UserNotFoundException;
import com.ecommerce.auth.mapper.UserMapper;
import com.ecommerce.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//Implementaion av UserService, här ligger logiken för att hämta användare från databasen
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    //Hämta alla användare
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    //Hämta användare baserat på id
    @Override
    public UserResponse getUserById(Long id) {
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toResponse(user);
    }

    
    
}
