package com.ecommerce.auth.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

//Entity som representerar en användare i auth-service
//Varje user objekt motsvarar en rad i tabellen "users"
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //Körs automatiskt innan användaren sparas för första gången
    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //Körs automatiskt innan användaren updateras
    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }





    
}
