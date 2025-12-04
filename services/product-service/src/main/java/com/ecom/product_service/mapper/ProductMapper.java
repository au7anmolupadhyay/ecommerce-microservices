package com.ecom.product_service.mapper;

import com.ecom.product_service.entity.ProductEntity;
import com.ecom.product_service.dto.ProductRequestDTO;
import com.ecom.product_service.dto.ProductResponseDTO;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toDTO(ProductEntity entity);
    List<ProductResponseDTO> toDTOList(List<ProductEntity> entities);
    ProductEntity toEntity(ProductRequestDTO entity);
}
