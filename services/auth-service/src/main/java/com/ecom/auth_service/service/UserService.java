package com.ecom.auth_service.service;

import com.ecom.auth_service.dto.UserDTO;
import com.ecom.auth_service.entity.UserEntity;
import com.ecom.auth_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserEntity register(UserEntity user){
        return userRepository.save(user);
    }

    public UserDTO findByEmail(String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(user.getEmail(), user.getRole());
    }

}
