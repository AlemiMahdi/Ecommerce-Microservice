package com.ecommerce.auth.exception;

import java.time.LocalDateTime;

import lombok.*;

//Standardformat för felmeddelanden från auth-service
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
