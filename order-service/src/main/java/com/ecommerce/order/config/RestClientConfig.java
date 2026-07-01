package com.ecommerce.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Konfiguration för HTTP-klienter.
 *
 * RestClient används för att order-service ska kunna anropa andra tjänster,
 * exempelvis product-service.
 */
@Configuration
public class RestClientConfig {
    
    //Skapar en RestClient med product-serivice som base URL
    @Bean
    public RestClient productRestClient(
        @Value("${product-service.base-url}") String productServiceBaseUrl
    ){
        return RestClient.builder()
                .baseUrl(productServiceBaseUrl)
                .build();
    }
}
