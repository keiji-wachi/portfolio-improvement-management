package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.UpdateRepository;

@Service
public class UpdateService {

    private UpdateRepository repository;

    public UpdateService(UpdateRepository repository){
        this.repository = repository;
    }

    public int update(int id, String name){
        return repository.update(id, name);
    }

}
