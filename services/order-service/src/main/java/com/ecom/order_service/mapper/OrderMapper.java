package com.ecom.order_service.mapper;

import com.ecom.order_service.dto.OrderResponseDTO;
import com.ecom.order_service.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

//    @Mapping(source = "orderStatus", target = "orderStatus")
    OrderResponseDTO toDto(OrderEntity entity);
}
