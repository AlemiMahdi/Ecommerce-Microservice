package com.ecommerce.product.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Standardformat för felmeddelanden som API:et returnerar.
 *
 * Detta gör att frontend alltid får fel i samma struktur.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    
    //Tidpunkt då felet inträffade.
    private LocalDateTime timestamp;

    //HTTP-statuskod, exempelvis 404 eller 400.
    private int status;

    //Http-statusens namn, exempelvis "NOT_FOUND"
    private String error;

    //Det faktiska felmeddelandet
    private String message;

    //Vilken URL/path som orsakadet felet
    private String path;

    

    
}
