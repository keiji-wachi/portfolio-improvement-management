package com.example.improvementmanagement.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateUserDto {

    private String employeeNo;
    private String name; 
    private Integer department_id;
    private Integer role_id;
    private String password;

    public CreateUserDto(String employeeNo, String name, Integer department_id, Integer role_id, String password){
        this.employeeNo = employeeNo;
        this.name = name;
        this.department_id = department_id;
        this.role_id = role_id;
        this.password = password;
    }

    @NotBlank
    public String getEmployeeNo(){
        return employeeNo;
    }
    @NotBlank
    public String getName(){
        return name;
    }
    @NotNull
    @Positive
    public Integer getDepartment_id(){
        return department_id;
    }
    @NotNull
    @Positive
    public Integer getRole_id(){
        return role_id;
    }
    @NotBlank
    @Size(min = 8)
   public String getPassword(){
        return password;
    }
}
