package com.ecom.user_service.repository;

import com.ecom.user_service.entity.UserAddressEntity;
import com.ecom.user_service.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {
    List<UserAddressEntity> findByUser(UserProfileEntity user);

    Optional<UserAddressEntity> findByIdAndUser(Long id, UserProfileEntity user);
}
