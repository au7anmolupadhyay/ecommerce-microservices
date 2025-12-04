package com.ecom.product_service.service;

import com.ecom.product_service.entity.ProductEntity;
import com.ecom.product_service.mapper.ProductMapper;
import com.ecom.product_service.repository.ProductRepository;
import com.ecom.product_service.dto.ProductRequestDTO;
import com.ecom.product_service.dto.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponseDTO addProduct(ProductRequestDTO product){
        ProductEntity productEntry =  productRepository.save(productMapper.toEntity(product));
        return productMapper.toDTO(productEntry);
    }

    public List<ProductResponseDTO> getAllProducts(){
        List<ProductEntity> allProducts = productRepository.findAll();
        return productMapper.toDTOList(allProducts);
    }

    public ProductResponseDTO getProductById(Long id){
        ProductEntity product = productRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Product not found!"));
        return productMapper.toDTO(product);
    }
}
