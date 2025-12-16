package com.ecom.user_service.dto;

import com.ecom.user_service.entity.UserAddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDto {
    private String name;
    private String phone;
}
