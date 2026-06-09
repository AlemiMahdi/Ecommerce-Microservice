package com.ecommerce.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO som används när klienten skickar data för att skapa eller uppdatera en produkt.
 * Vi använder DTO istället för Entity direkt i API:et för bättre säkerhet och kontroll.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;
    private String description;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Product category is required")
    private String category;

    private String imageUrl;
}
