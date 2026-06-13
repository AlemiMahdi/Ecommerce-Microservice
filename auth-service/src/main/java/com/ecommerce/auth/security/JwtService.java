package com.ecommerce.auth.security;

import com.ecommerce.auth.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * Service som skapar JWT-tokens.
 *
 * En JWT-token används senare av frontend för att bevisa
 * att användaren är inloggad.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    //Skapar en hemlig nyckel från jwt.secrete i application.properties
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    //Skapar en JWT-token för en användare
    public String generateToken(User user){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    //Läser alla claims från tokenen
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Hämtar username från tokenens subject
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    //Hämtar rollen från tokenen
    public String exctractRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    //Kontrollerar om tokenen har gått ut
    private boolean isTokenExpired(String token){
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    //Kontrollerar om tokenen är giltig
    public boolean isTokenValid(String token){
        try {
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}


