package com.ecommerce.order.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import com.ecommerce.order.dto.ProductInfoResponse;
import com.ecommerce.order.exception.ProductNotAvailableException;
import com.ecommerce.order.exception.ProductserviceException;

import lombok.RequiredArgsConstructor;

/**
 * Client-klass som ansvarar för kommunikation med product-service.
 *
 * På detta sätt slipper OrderServiceImpl känna till exakt hur HTTP-anropet görs.
 */
@Component
@RequiredArgsConstructor
public class ProductClient {
    
    private final RestClient productRestClient;

    //Hämtar en produkt från product-service via productId
    public ProductInfoResponse getProductById(Long productId){
        try {
            ProductInfoResponse product = productRestClient
                    .get()
                    .uri("/api/v1/products/{id}", productId)
                    .retrieve()
                    .body(ProductInfoResponse.class);
        if (product == null || product.getId() == null || product.getPrice() == null) {
            throw new ProductNotAvailableException(productId);
        }
        
        return product;

        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().value() == 404) {
                throw new ProductNotAvailableException(productId);
            }

            throw new ProductserviceException(
                "Could not fetch product from product-service"
            );

        } catch(Exception exception){
            throw new ProductserviceException(
                "Product-service is not available"
            );
        }
    }
}
