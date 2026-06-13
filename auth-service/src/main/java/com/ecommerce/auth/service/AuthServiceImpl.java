package com.ecommerce.auth.service;

import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.security.JwtService;
import com.ecommerce.auth.exception.EmailAlreadyExistsException;
import com.ecommerce.auth.exception.InvalidCredentialsException;
import com.ecommerce.auth.exception.UsernameAlreadyExistsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.dto.AuthResponse;
import com.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.auth.entity.Role;
import com.ecommerce.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//Här lägger vi logiken för register och login
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //Registrerar en ny användare
    @Override
    public AuthResponse register(RegisterRequest request) {

        //Kontrollerar om användarnamnet redan finns
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        //Kontrollerar om email redan finns
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        //Skapar en ny användare
        User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .username(request.getUsername())
            .email(request.getEmail())

            //Viktigt: lösenordet hashas innan det sparas
            .password(passwordEncoder.encode(request.getPassword()))

            //Alla nya användare blir vanliga users från början
            .role(Role.ROLE_USER)
            .build();
        User savedUser = userRepository.save(user);

        return AuthResponse.builder()
            .userId(savedUser.getId())
            .username(savedUser.getUsername())
            .email(savedUser.getEmail())
            .role(savedUser.getRole().name())

            //JWT kommer senare, därför är token null just nu
            .token(jwtService.generateToken(savedUser))
            .build();
        
    }

    //Loggar in en användare
    @Override
    public AuthResponse login(LoginRequest request) {
        
        //Hämtar anbändare från databasen baserat på username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(InvalidCredentialsException::new);
        
        //Jämför lösenordet från requesten med det hashade lösenordet i databasen
        boolean passwordMatches = passwordEncoder.matches(
            request.getPassword(), 
            user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        return AuthResponse.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole().name())

            //JWT kommer senare
            .token(jwtService.generateToken(user))
            .build();
    }

    
}