package com.ecom.product_service.controller;

import com.ecom.product_service.service.ProductService;
import com.ecom.product_service.dto.ProductRequestDTO;
import com.ecom.product_service.dto.ProductResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(productService.getProducts(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
    }
    
}
