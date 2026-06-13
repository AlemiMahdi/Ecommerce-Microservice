package com.ecommerce.auth.dto;

import lombok.*;

//DTO som returneras efter lyckad login eller registrering, senare lägs JWT-token här
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String token;   
}
