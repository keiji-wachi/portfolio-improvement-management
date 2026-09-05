package com.example.improvementmanagement.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserUpdateDto {

    @NotBlank
    private String name; 

    @NotNull
    @Positive
    private Integer departmentId;

    @NotNull
    @Positive
    private Integer roleId;

    public UserUpdateDto(String name, Integer departmentId, Integer roleId){
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
    }

    public String getName(){
        return name;
    }

    public Integer getDepartmentId(){
        return departmentId;
    }

    public Integer getRoleId(){
        return roleId;
    }
}
