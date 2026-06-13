package com.ecommerce.auth.dto;

import java.time.LocalDateTime;

import lombok.*;

//DTO som används när vi returnerar användardata från API:et, vi returnerar inte password här!
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
