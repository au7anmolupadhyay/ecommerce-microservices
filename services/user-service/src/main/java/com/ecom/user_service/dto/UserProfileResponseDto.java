package com.ecom.user_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserProfileResponseDto {
    private String authUserId;
    private String name;
    private String phone;
    private List<UserAddressResponseDto> addresses;
    private UserPreferencesResponseDto preferences;
}
