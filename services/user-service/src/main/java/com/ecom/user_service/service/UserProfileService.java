package com.ecom.user_service.service;

import com.ecom.user_service.dto.UserProfileResponseDto;
import com.ecom.user_service.entity.UserAddressEntity;
import com.ecom.user_service.entity.UserPreferencesEntity;
import com.ecom.user_service.entity.UserProfileEntity;
import com.ecom.user_service.repository.UserAddressRepository;
import com.ecom.user_service.repository.UserPreferenceRepository;
import com.ecom.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserPreferenceRepository userPreferenceRepository;


    public UserProfileEntity getOrCreateUser(String userId) {
        return userProfileRepository.findByAuthUserId(userId)
                .orElseGet(()->{
                    UserProfileEntity user = new UserProfileEntity();
                    user.setAuthUserId(userId);
                    user.setName(null);
                    user.setPhone(null);
                    return userProfileRepository.save(user);
                });
    }

    public UserProfileResponseDto getProfile(String userId){
        UserProfileEntity user = getOrCreateUser(userId);

        List<UserAddressEntity> addresses = userAddressRepository.findByUser(user);
        UserPreferencesEntity prefs = userPreferenceRepository.findByUser(user);

    }
}
