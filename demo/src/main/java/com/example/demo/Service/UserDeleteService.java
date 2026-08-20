package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserDeleteRepository;

@Service
public class UserDeleteService {
    private UserDeleteRepository repository;

    public UserDeleteService(UserDeleteRepository repository){
        this.repository = repository;
  
    }
    
    public int deleteUser(int id){
        return repository.deleteUser(id);
    }
}
