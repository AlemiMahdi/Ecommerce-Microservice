package com.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity som representerar en kundorder.
 * Vi kallar klassen CustomerOrder istället för Order eftersom ORDER är ett SQL-nyckelord.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID för användaren från auth-service.
     * Viktigt:
     * Detta är inte en foreign key till auth_db.
     * Order-service sparar bara userId som referens.
     */
    @Column(nullable = false)
    private Long userId;

    //Orderns status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    //Totalbelopp för hela ordern
    @Column(nullable = false)
    private BigDecimal totalAmount;

    /**
     * Alla orderrader som tillhör ordern.
     *
     * CascadeType.ALL betyder att orderrader sparas tillsammans med ordern.
     * orphanRemoval=true betyder att orderrader tas bort om de tas bort från listan.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    //När ordern skapades
    private LocalDateTime createdAt;

    //När ordern senaste uppdaterades
    private LocalDateTime updatedAt;

    //Körs automatiskt innan ordern sparas första gången
    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

    //Körs automatiskt innan ordern uppdateras
    @PreUpdate
    public void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
