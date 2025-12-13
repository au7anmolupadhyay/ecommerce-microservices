package com.ecom.user_service.dto;

import com.ecom.user_service.entity.UserAddressEntity;
import com.ecom.user_service.entity.UserPreferencesEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileResponseDto {
    private String authUserId;
    private String name;
    private String phone;
    private List<UserAddressResponseEntity> addresses;
    private UserPreferencesResponseEntity preferences;
}
