package com.example.demo.controller;

import com.example.demo.Dto.CRUDDto;
import com.example.demo.Service.CreateService;
import com.example.demo.Service.DeleteService;
import com.example.demo.Service.ReadService;
import com.example.demo.Service.UpdateService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TestController {


    private final ReadService readService;
    private final CreateService createService;
    private final DeleteService deleteService;
    private final UpdateService updateService;

    public TestController(CreateService createService, ReadService readService, DeleteService deleteService, UpdateService updateService){
        this.createService = createService;
        this.readService = readService;
        this.deleteService = deleteService;
        this.updateService = updateService;
    }

    @GetMapping("/api/read")
    public List<CRUDDto> read() {
        return readService.findAll();
    }

    @PostMapping("/api/create")
    public int create(@RequestParam String name) {
        return createService.create(name);
    }

    @DeleteMapping("/api/delete")
    public int delete(@RequestParam int id) {
        return deleteService.delete(id);
    }

    @PutMapping("/api/update")
    public int update(@RequestParam int id, @RequestParam String name) {
        return updateService.update(id, name);
    }
    
    
    
}
