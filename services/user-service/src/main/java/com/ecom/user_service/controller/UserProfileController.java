package com.ecom.user_service.controller;

import com.ecom.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader("X-USER-ID") String userId){
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestHeader("X-USER-ID") String userId,
                                               @RequestBody UserProfileRequestDto profile){
        return ResponseEntity.ok(userProfileService.updateUserProfile(userId, profile));
    }

    @GetMapping("/addresses")
    public ResponseEntity<?> getAllAddresses(@RequestHeader("X-USER-ID") String userId){
        return ResponseEntity.ok(userProfileService.getAllAddresses(userId));
    }

    @PostMapping("/addresses")
    public ResponseEntity<?> addAddresses(@RequestHeader("X-USER-ID") String userId,
                                          @RequestBody UserAddressRequestDto addresses){
        return ResponseEntity.created(userProfileService.addAddresses(userId, addresses));
    }

    @DeleteMapping("/addresses")
    public ResponseEntity<?> deleteAddress(@RequestHeader("X-USER-ID") String userId){
        return ResponseEntity.noContent(userProfileService.deleteAddress(userId));
    }
}
