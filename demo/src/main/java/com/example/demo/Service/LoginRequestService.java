package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Repository.LoginRequestRepository;

@Service
public class LoginRequestService {

    private final LoginRequestRepository repository;

    public LoginRequestService(LoginRequestRepository repository){
        this.repository = repository;
    }

    public LoginResponseDto loginAuth(LoginRequestDto dto){
        return repository.loginAuth(dto);
    }
}
