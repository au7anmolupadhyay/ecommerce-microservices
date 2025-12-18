package com.ecom.user_service.service;

import com.ecom.user_service.dto.*;
import com.ecom.user_service.entity.UserAddressEntity;
import com.ecom.user_service.entity.UserPreferencesEntity;
import com.ecom.user_service.entity.UserProfileEntity;
import com.ecom.user_service.repository.UserAddressRepository;
import com.ecom.user_service.repository.UserPreferenceRepository;
import com.ecom.user_service.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserPreferenceRepository userPreferenceRepository;

    private UserAddressResponseDto mapAddressToResponse(
            UserAddressEntity entity) {

        UserAddressResponseDto dto = new UserAddressResponseDto();
        dto.setId(entity.getId());
        dto.setLine1(entity.getLine1());
        dto.setLine2(entity.getLine2());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setPincode(entity.getPincode());
        dto.setCountry(entity.getCountry());
        dto.setDefault(entity.isDefault());
        return dto;
    }

    private UserProfileEntity getOrCreateUser(String userId) {
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
        UserPreferencesEntity prefs = userPreferenceRepository.findByUser(user).orElse(null);

        UserProfileResponseDto response = new UserProfileResponseDto();
        response.setAuthUserId(user.getAuthUserId());
        response.setName(user.getName());
        response.setPhone(user.getPhone());

        List<UserAddressResponseDto> addressResponse =
                addresses.stream()
                        .map(this::mapAddressToResponse)
                        .toList();

        response.setAddresses(addressResponse);

        if(prefs != null){
            UserPreferencesResponseDto prefDto = new UserPreferencesResponseDto();
            prefDto.setNotificationsEnabled(prefs.isNotificationsEnabled());
            prefDto.setLanguage(prefs.getLanguage());

            response.setPreferences(prefDto);
        }

        return response;
    }

    public UserProfileResponseDto updateUserProfile(String userId, UserProfileRequestDto profile){

        UserProfileEntity user = getOrCreateUser(userId);

        user.setName(profile.getName());
        user.setPhone(profile.getPhone());

        userProfileRepository.save(user);
        return getProfile(userId);

    }

    public List<UserAddressResponseDto> getAllAddresses(String userId){
        UserProfileEntity user = getOrCreateUser(userId);
        List<UserAddressEntity> addresses = userAddressRepository.findByUser(user);

        return addresses.stream()
                .map(this::mapAddressToResponse)
                .toList();
    }

    public UserAddressResponseDto addAddress(String userId, UserAddressRequestDto addressRequest){
        UserProfileEntity user = getOrCreateUser(userId);

        List<UserAddressEntity> existingAddresses = userAddressRepository.findByUser(user);

        if(existingAddresses.isEmpty()){
            addressRequest.setDefault(true);
        }
        else if(addressRequest.isDefault()){
            for(UserAddressEntity address : existingAddresses){
                if(address.isDefault()){
                    address.setDefault(false);
                    break;
                }
            }
            userAddressRepository.saveAll(existingAddresses);
        }

        UserAddressEntity address = new UserAddressEntity();
        address.setUser(user);
        address.setLine1(addressRequest.getLine1());
        address.setLine2(addressRequest.getLine2());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setPincode(addressRequest.getPincode());
        addressRequest.setCountry(addressRequest.getCountry());

        return mapAddressToResponse(address);
    }
}
