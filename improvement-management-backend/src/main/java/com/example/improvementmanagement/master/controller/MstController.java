package com.example.improvementmanagement.master.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.improvementmanagement.master.dto.DepartmentDto;
import com.example.improvementmanagement.master.dto.RoleDto;
import com.example.improvementmanagement.master.service.MstService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/msts")
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")

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
