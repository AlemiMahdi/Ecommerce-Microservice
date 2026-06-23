package com.ecommerce.order.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representerar en orderrad.
 *
 * En order kan innehålla flera produkter.
 * Varje OrderItem sparar produktens id, namn, antal och pris vid köptillfället.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID för produkten från product-service.
     *
     * Viktigt:
     * Detta är inte en foreign key till product_db.
     * Vi sparar bara produktens id som referens.
     */
    @Column(nullable = false)
    private Long productId;

    /**
     * Produktnamnet vid beställningstillfället.
     * Vi sparar detta som snapshot eftersom produktnamnet kan ändras senare.
     */
    @Column(nullable = false)
    private String productName;

    //Antal examplar kuden beställer
    @Column(nullable = false)
    private Integer quantity;

    //Produktens pris vid beställningstillfället
    @Column(nullable = false)
    private BigDecimal unitPrice;

    //Totala priset
    @Column(nullable = false)
    private BigDecimal subtotal;


}
