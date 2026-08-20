package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.CRUDDto;
import com.example.demo.Repository.ReadRepository;

@Service
public class ReadService {

    private final ReadRepository readRepository;

    public ReadService(ReadRepository readRepository){
        this.readRepository = readRepository;
    }

    public List<CRUDDto> findAll(){
        return readRepository.findAll();
    }

}
