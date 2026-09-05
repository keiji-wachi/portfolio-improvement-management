package com.example.improvementmanagement.auth.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.improvementmanagement.auth.dto.LoginRequestDto;
import com.example.improvementmanagement.auth.dto.LoginResponseDto;
import com.example.improvementmanagement.auth.service.LoginRequestService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/login")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class LoginController {

    private final LoginRequestService loginRequestService;

    public LoginController(LoginRequestService loginRequestService) {
        this.loginRequestService = loginRequestService;
    }

    @PostMapping
    public LoginResponseDto loginAuth(
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletRequest request,
            HttpServletResponse response) {

        return loginRequestService.loginAuth(dto, request, response);
    }
}