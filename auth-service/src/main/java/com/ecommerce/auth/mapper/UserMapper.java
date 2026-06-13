package com.ecommerce.auth.mapper;

import com.ecommerce.auth.dto.UserResponse;
import com.ecommerce.auth.entity.User;

/**
 * Mapper som konverterar User entity till UserResponse DTO.
 *
 * Viktigt:
 * Vi skickar aldrig tillbaka password till frontend.
 */
public class UserMapper {
    
    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
