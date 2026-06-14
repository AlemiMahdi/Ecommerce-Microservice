package com.ecommerce.gateway.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Enkel JWT-service för api-gateway.
 *
 * Den validerar tokens som auth-service har skapat.
 */
@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    //Skapar signeringsnyckel från samma secret som auth-service använder
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Läs
     * er claims från tokenen.
     * Om tokenen är felaktig, manipulerad eller utgången
     * kastar JJWT en exception.
     */
    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Returnerar true om tokenen är giltig
    public boolean isTokenValid(String token){
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}
