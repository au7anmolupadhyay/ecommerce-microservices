package com.ecom.user_service.dto;

import lombok.Data;

@Data
public class UserAddressResponseDto {
    private Long id;
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String isDefault;
}
