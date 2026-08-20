package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.RoleDto;
import com.example.demo.Service.MstService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/msts")
@CrossOrigin(origins = "http://localhost:5173")

public class MstController {
    
    private final MstService mstService;

    public  MstController(MstService mstService){
        this.mstService = mstService;
    }

    @GetMapping("/departments")
    public List<DepartmentDto> getDepartments() {
        return mstService.getDepartments();
    }
    
    @GetMapping("/roles")
    public List<RoleDto> getRoles() {
        return mstService.getRoles();
    }
    
}
