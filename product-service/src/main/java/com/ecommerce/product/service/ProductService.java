package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;

/**
 * Interface för product-service.
 *
 * Här definierar vi vilka funktioner produktmodulen ska erbjuda.
 * Själva logiken skrivs i ProductServiceImpl.
 */
public interface ProductService {
    
    /**
     * Skapar en ny produkt.
     *
     * @param request data som kommer från klienten
     * @return skapad produkt som response DTO
     */
    ProductResponse createProduct(ProductRequest product);

    /**
     * Hämtar alla produkter.
     *
     * @return lista med alla produkter
     */
    List<ProductResponse> getAllProducts();

    /**
     * Hämtar en produkt baserat på id.
     *
     * @param id produktens id
     * @return produkten om den finns
     */
    ProductResponse getProductById(Long id);

    /**
     * Uppdaterar en befintlig produkt.
     *
     * @param id produktens id
     * @param request ny produktdata
     * @return uppdaterad produkt
     */
    ProductResponse updateProduct(Long id, ProductRequest product);

    /**
     * Tar bort en produkt.
     *
     * @param id produktens id
     */
    void deleteProduct(Long id);

    /**
     * Hämtar produkter baserat på kategori.
     *
     * @param category kategori, exempelvis "Electronics"
     * @return produkter inom vald kategori
     */
    List<ProductResponse> getProductsByCategory(String category);
    

}
