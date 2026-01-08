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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        address.setCountry(addressRequest.getCountry());
        address.setDefault(addressRequest.isDefault());

        userAddressRepository.save(address);
        return mapAddressToResponse(address);
    }

    public Boolean deleteAddress(String userId, Long addressId) {

        UserProfileEntity user = getOrCreateUser(userId);

        UserAddressEntity address = userAddressRepository.findByIdAndUser(addressId, user)
                .orElseThrow(()-> new RuntimeException("Address not found"));

        boolean wasDefault = address.isDefault();

        userAddressRepository.delete(address);

        if(wasDefault){
            List<UserAddressEntity> remainingAddresses = userAddressRepository.findByUser(user);

            if(!remainingAddresses.isEmpty()){
                UserAddressEntity newDefaultAddress = remainingAddresses.get(0);
                newDefaultAddress.setDefault(true);
                userAddressRepository.save(newDefaultAddress);
            }
        }

        return true;
    }

    public UserPreferencesResponseDto getPreferences(String userId) {
            UserProfileEntity user = getOrCreateUser(userId);

            UserPreferencesEntity prefs = userPreferenceRepository.findByUser(user)
                    .orElse(null);

            UserPreferencesResponseDto responseDto = new UserPreferencesResponseDto();

            if(prefs != null){
                responseDto.setNotificationsEnabled(prefs.isNotificationsEnabled());
                responseDto.setLanguage(prefs.getLanguage());
            }
            else{
                responseDto.setNotificationsEnabled(true);
                responseDto.setLanguage("en");
            }

            return responseDto;
    }

    public UserPreferencesResponseDto updatePreferences(String userId, UserPreferencesRequestDto request) {

        UserProfileEntity user = getOrCreateUser(userId);

        UserPreferencesEntity prefs =
                userPreferenceRepository.findByUser(user)
                        .orElseGet(() -> {
                            UserPreferencesEntity newPrefs = new UserPreferencesEntity();
                            newPrefs.setUser(user);
                            return newPrefs;
                        });

        prefs.setNotificationsEnabled(request.isNotificationsEnabled());
        prefs.setLanguage(request.getLanguage());

        UserPreferencesEntity saved =
                userPreferenceRepository.save(prefs);

        UserPreferencesResponseDto response = new UserPreferencesResponseDto();
        response.setNotificationsEnabled(saved.isNotificationsEnabled());
        response.setLanguage(saved.getLanguage());

        return response;
    }

    @Transactional
    public UserAddressResponseDto setDefaultAddress(String userId, Long addressId) {

        UserProfileEntity user = getOrCreateUser(userId);

        // 1. Validate ownership
        UserAddressEntity target =
                userAddressRepository.findByIdAndUser(addressId, user)
                        .orElseThrow(() -> new RuntimeException("Address not found"));

        // 2. If already default → no-op
        if (target.isDefault()) {
            return mapAddressToResponse(target);
        }

        // 3. Remove existing default
        List<UserAddressEntity> addresses = userAddressRepository.findByUser(user);
        for (UserAddressEntity addr : addresses) {
            if (addr.isDefault()) {
                addr.setDefault(false);
                break;
            }
        }

        // 4. Set new default
        target.setDefault(true);

        // 5. Persist changes
        userAddressRepository.saveAll(addresses);
        userAddressRepository.save(target);

        return mapAddressToResponse(target);
    }

}
