package com.ecommerce.product.mapper;
/**
 * Mapper-klass som konverterar mellan Entity och DTO.
 * Det håller controller och service renare.
 */

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.entity.Product;

public class ProductMapper {
    
    /**
     * Konverterar ProductRequest till Product entity.
     */
    public static Product toEntity(ProductRequest request){
        return Product.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .category(request.getCategory())
                    .imageUrl(request.getImageUrl())
                    .build();
        
        
    }
 
    /**
    * Konverterar ProductRequest till Product entity.
    */
    public static ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
