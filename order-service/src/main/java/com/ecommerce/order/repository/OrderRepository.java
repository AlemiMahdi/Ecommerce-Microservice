package com.ecommerce.order.repository;

import com.ecommerce.order.entity.CustomerOrder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository för CustomerOrder.
 *
 * JpaRepository ger färdiga metoder:
 * save, findAll, findById, deleteById osv.
 */
@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long>{
    
    //Hämtar alla orders som tillhör en specifik användare
    List<CustomerOrder> findByUserId(Long userId);
}
