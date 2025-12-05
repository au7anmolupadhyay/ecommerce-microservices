package com.ecom.order_service.mapper;

import com.ecom.order_service.dto.OrderResponseDTO;
import com.ecom.order_service.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDTO toDto(OrderEntity entity);

}
