package com.ecom.user_service.dto;

import lombok.Data;

@Data
public class UserPreferencesResponseDto {
    private boolean notificationsEnabled;
    private String language;
}
