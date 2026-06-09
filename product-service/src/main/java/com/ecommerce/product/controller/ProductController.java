package com.ecommerce.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller-klassen ansvarar för HTTP-endpoints för produkter.
 *
 * Frontend, Postman eller andra tjänster skickar requests hit.
 * Controllern ska inte innehålla affärslogik.
 * Den skickar bara vidare arbetet till service-lagret.
 */

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Skapar en ny produkt.
     *
     * URL:
     * POST /api/v1/products
     *
     * @param request produktdata från klienten
     * @return skapad produkt
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
        @Valid @RequestBody ProductRequest request
    ){
        ProductResponse createdProduct = productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    /**
     * Hämtar alla produkter.
     *
     * URL:
     * GET /api/v1/products
     *
     * @return lista med alla produkter
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Hämtar en produkt baserat på id.
     *
     * URL:
     * GET /api/v1/products/{id}
     *
     * Exempel:
     * GET /api/v1/products/1
     *
     * @param id produktens id
     * @return produkt med valt id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
        @PathVariable Long id
    ){
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Uppdaterar en befintlig produkt.
     *
     * URL:
     * PUT /api/v1/products/{id}
     *
     * Exempel:
     * PUT /api/v1/products/1
     *
     * @param id produktens id
     * @param request ny produktdata
     * @return uppdaterad produkt
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductRequest request
    ){
        ProductResponse updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Tar bort en produkt.
     *
     * URL:
     * DELETE /api/v1/products/{id}
     *
     * Exempel:
     * DELETE /api/v1/products/1
     *
     * @param id produktens id
     * @return 204 No Content om borttagningen lyckas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Hämtar produkter inom en specifik kategori.
     *
     * URL:
     * GET /api/v1/products/category/{category}
     *
     * Exempel:
     * GET /api/v1/products/category/electronics
     *
     * @param category kategori
     * @return produkter inom kategorin
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable String category
    ){
        List<ProductResponse> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
    
}
