package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Repository.UserUpadateRepository;

@Service
public class UserUpdateService {

    private UserUpadateRepository repository;

    public UserUpdateService(UserUpadateRepository repository){
        this.repository = repository;
    }

    public int userUpdate(int id, UserUpdateDto dto){
        return repository.userUpdate(id, dto);
    }

}
