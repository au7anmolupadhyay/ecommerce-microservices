package com.ecom.auth_service.service;

import com.ecom.auth_service.dto.AuthResponseDTO;
import com.ecom.auth_service.dto.LoginRequestDTO;
import com.ecom.auth_service.dto.RegisterRequestDTO;
import com.ecom.auth_service.repository.UserRepository;
import com.ecom.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecom.auth_service.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequestDTO request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            return "User already exists";
        }

        try{
            UserEntity user = UserEntity.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();

            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return "User Registered Successfully";
    }

    public AuthResponseDTO login(LoginRequestDTO request){
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException( "User not found!"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("invalid password");
        }

            String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            return new AuthResponseDTO(accessToken, refreshToken);
    }
}
