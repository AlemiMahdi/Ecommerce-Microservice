package com.ecommerce.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//Säkerhetskonfiguration for auth-service
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    //PasswordEncoder används för att hasha lösenord. BCrypt gör så att vi inte sparar lösenord i klartext i databasen.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * SecurityFilterChain styr vilka endpoints som är öppna och skyddade.
     *
     * CSRF stängs av just nu eftersom vi bygger ett REST API
     * som senare ska använda JWT istället för session/form-login.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                
                //Jwt är stateless och det betyder att server inte spara login sessioner
                .sessionManagement(session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                //Om användare saknar giltig token returnerar vi 401
                .exceptionHandling( exception ->
                        exception.authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })   
                )
                .authorizeHttpRequests(auth -> auth
                    //Register, login och swagger ska vara öppna
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    //User endpoitns kräven nu JWT-token
                    .requestMatchers("/api/v1/users/**").authenticated()
                    //Alla andra enpoitns kräven ocks autentisering
                    .anyRequest().authenticated()
                )
                //Vårt JWT-filter ska köras innan Spring Securitys vanliga username/password-filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
