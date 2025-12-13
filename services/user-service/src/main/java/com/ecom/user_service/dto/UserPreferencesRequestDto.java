package com.ecom.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPreferencesRequestDto {
    private boolean notificationsEnabled;
    private String language;
}
