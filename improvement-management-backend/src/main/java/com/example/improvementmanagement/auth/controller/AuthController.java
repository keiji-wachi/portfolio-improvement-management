package com.example.improvementmanagement.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.improvementmanagement.auth.dto.LoginResponseDto;
import com.example.improvementmanagement.auth.security.CustomUserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDto> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        LoginResponseDto response = new LoginResponseDto(
                userDetails.getUserId(),
                userDetails.getName(),
                userDetails.getDepartmentId(),
                userDetails.getDepartmentName(),
                userDetails.getRoleId(),
                userDetails.getFirstLoginFlag(),
                true
        );

        return ResponseEntity.ok(response);
    }
}