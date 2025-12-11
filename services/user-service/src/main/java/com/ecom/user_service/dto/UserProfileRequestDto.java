package com.ecom.user_service.dto;

import com.ecom.user_service.entity.UserAddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileRequestDto {
    private String name;
    private String phone;
    private UserAddressEntity address;
}
