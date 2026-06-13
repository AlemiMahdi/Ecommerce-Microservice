package com.ecommerce.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //Hämtar användare baserat på username, behövs senare vid login
    Optional<User> findByUsername(String username);

    //Kontrollerar om en username finns redan och det behövs vid registrering
    boolean existsByUsername(String username);

    //Kontrollerar om en email finns redan vilket behövs vid registrering
    boolean existsByEmail(String email);

}
