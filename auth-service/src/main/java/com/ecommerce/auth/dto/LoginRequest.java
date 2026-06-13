package com.ecommerce.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

//DTO som används när en användare loggar in
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
