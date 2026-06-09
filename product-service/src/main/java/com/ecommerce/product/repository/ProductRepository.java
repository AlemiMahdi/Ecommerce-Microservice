package com.ecommerce.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.product.entity.Product;

/**
 * Repository-lagret ansvarar för all databasinteraktion för Product.
 * JpaRepository ger oss färdiga metoder som save, findAll, findById och deleteById.
 */

@Repository
public interface ProductRepository extends JpaRepository <Product, Long> {
    
    /**
     * Exempel på custom query method.
     * Spring Data JPA skapar automatiskt query baserat på metodnamnet.
     */
    List<Product> findByCategoryIgnoreCase(String category);    

}
