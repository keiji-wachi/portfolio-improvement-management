package com.example.demo.Service;

import org.springframework.stereotype.Service;
import com.example.demo.Repository.DeleteRepository;

@Service
public class DeleteService {

    private DeleteRepository repository;

    public DeleteService(DeleteRepository repository){
        this.repository = repository;
  
    }
    
    public int delete(int id){
        return repository.delete(id);
    }



}
