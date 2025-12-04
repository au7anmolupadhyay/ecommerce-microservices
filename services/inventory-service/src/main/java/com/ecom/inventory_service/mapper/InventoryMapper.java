package com.ecom.inventory_service.mapper;

import com.ecom.inventory_service.dto.StockCheckResponseDTO;
import com.ecom.inventory_service.dto.StockResponseDTO;
import com.ecom.inventory_service.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    StockResponseDTO toDto(InventoryEntity entity);
    StockCheckResponseDTO toResponseDto(InventoryEntity entity);
}
