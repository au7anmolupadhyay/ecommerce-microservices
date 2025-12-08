package com.ecom.auth_service.controller;

import com.ecom.auth_service.dto.AuthResponseDTO;
import com.ecom.auth_service.dto.LoginRequestDTO;
import com.ecom.auth_service.dto.RegisterRequestDTO;
import com.ecom.auth_service.dto.UserDTO;
import com.ecom.auth_service.service.AuthService;
import com.ecom.auth_service.service.UserService;
import com.ecom.auth_service.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, UserService userService, JwtUtil jwtUtil){
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "Health - okay";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO user){
        return authService.register(user);
    }

    @GetMapping("/get/{email}")
    public UserDTO getUser(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO user){
        return authService.login(user);
    }

    @GetMapping("/validate")
    public String validate(@RequestHeader("Authorization") String token){
        return jwtUtil.validateToken(token);
    }
}
