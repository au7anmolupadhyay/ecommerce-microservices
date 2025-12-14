package com.ecom.user_service.repository;

import com.ecom.user_service.entity.UserPreferencesEntity;
import com.ecom.user_service.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreferencesEntity, Long> {
    Optional<UserPreferencesEntity> findByUser(UserProfileEntity user);
}
