package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.CreateUserDto;
import com.example.demo.Repository.CreateUserRepository;

@Service
public class UserCreateService {

    private final CreateUserRepository repository;

    public UserCreateService(CreateUserRepository repository){
    this.repository = repository;
    }

    public int createUser(CreateUserDto dto){
        return repository.createUser(dto);
    }
}