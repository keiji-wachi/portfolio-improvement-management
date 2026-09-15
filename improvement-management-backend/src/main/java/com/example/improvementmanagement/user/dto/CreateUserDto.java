package com.example.improvementmanagement.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateUserDto {

    private String employeeNo;
    private String name; 
    private Integer departmentId;
    private Integer roleId;
    private String password;

    public CreateUserDto(String employeeNo, String name, Integer departmentId, Integer roleId, String password){
        this.employeeNo = employeeNo;
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
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
    public Integer getDepartmentId(){
        return departmentId;
    }
    @NotNull
    @Positive
    public Integer getRoleId(){
        return roleId;
    }
    @NotBlank
    @Size(min = 8)
   public String getPassword(){
        return password;
    }
}
