package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.CreateRepository;

@Service
public class CreateService {

    private final CreateRepository repository;

    public CreateService(CreateRepository repository){
        this.repository = repository;
    }

    public int create(String name){
        return repository.insert(name);
    }
}
