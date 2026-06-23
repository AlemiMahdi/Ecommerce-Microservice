package com.ecommerce.order.dto;

import java.math.BigDecimal;

import lombok.*;

//DTO som returnerar information om en orderrad
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
