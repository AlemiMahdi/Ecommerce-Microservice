package com.ecommerce.gateway.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * GlobalFilter körs på requests som går genom gatewayn.
 *
 * Denna filter kontrollerar JWT-token för skyddade routes.
 */
@Component
@RequiredArgsConstructor
public class JwtGatewayFilter implements GlobalFilter, Ordered{

    private final JwtService jwtService;

    //Själva filter-logiken
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        //Öppna endpoints får användas utan token.
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }
        
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");
        
        //Om Auth-header saknas eller inte börjar med beareer stoppas requesten direkt i gatewayn
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            return unauthorized(exchange);
        }

        //Här läses claims från token som sedan skickas vidare username, userid och role som headers, 
        //detta gör att andra tjänster senare kan läsa vem användaren är
        Claims claims = jwtService.extractClaims(token);
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Name", claims.getSubject())
                .header("X-User-Id", String.valueOf(claims.get("userId")))
                .header("X-User-Role", String.valueOf(claims.get("role")))
                .build();
        return chain.filter(
                exchange.mutate()
                        .request(mutatedRequest)
                        .build()
        );
    }

    //Bestämmer vilka path som är öppna
    private boolean isPublicPath(String path) {
    return path.startsWith("/api/v1/auth/")
            || path.equals("/api/v1/products")
            || path.startsWith("/api/v1/products/");
}
    
    //Returnerar 401 Unauthorized
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    //Filter-order, lägre värde betyder att filtret körs tidigare
    @Override
    public int getOrder() {
        return -1;
    }

    

    

}