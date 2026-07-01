package com.ecommerce.order.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

/**
 * DTO som används av order-service när den hämtar produktdata från product-service.
 *
 * Vi behöver bara id, name och price.
 * Product-service kan returnera fler fält, men de ignoreras.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInfoResponse {
    
    private Long id;
    private String name;
    private BigDecimal price;
}
