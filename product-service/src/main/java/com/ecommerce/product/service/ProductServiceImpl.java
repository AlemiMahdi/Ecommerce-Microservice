package com.ecommerce.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

/**
     * Implementation av ProductService.
     *
     * Denna klass innehåller affärslogiken för produkter.
     */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    /**
     * Repository injiceras via konstruktor tack vare @RequiredArgsConstructor.
     *
     * final betyder att repository måste sättas när objektet skapas.
     */
    private final ProductRepository productRepository;
    
    //Skapar en ny produkt
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        //konverterar request DTO till entity
        Product product = ProductMapper.toEntity(request);
        //Spara produkten i databasen
        Product savedProduct = productRepository.save(product);
        //Konverterar entity till response DTO
        return ProductMapper.toResponse(savedProduct);
    }

    
    //Hämtar alla produkter. 
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                    .stream()
                    .map(ProductMapper::toResponse)
                    .toList();
    }

    //Hämtar en produkt med ID
    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toResponse(product);
    }

    //Uppdaterar en produkt
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setImageUrl(request.getImageUrl());

        Product updatedProduct = productRepository.save(existingProduct);

        return ProductMapper.toResponse(updatedProduct);
    }

    //Ta bort en produkt
    @Override
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(existingProduct);
    }

    //Hämtar produkter baserat på kategori
    @Override
    public List<ProductResponse> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    
    
}
