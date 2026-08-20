package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Service.LoginRequestService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
    private final LoginRequestService loginRequestService;

    public LoginController(LoginRequestService loginRequestService){
        this.loginRequestService = loginRequestService;
    }

    @PostMapping
    public LoginResponseDto loginAuth(@RequestBody LoginRequestDto dto) {
        return loginRequestService.loginAuth(dto);
    }
    
}
