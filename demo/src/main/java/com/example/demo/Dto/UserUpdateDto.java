package com.example.demo.Dto;

public class UserUpdateDto {
    private String name; 
    private int departmentId;
    private int roleId;

    public UserUpdateDto(String name, int departmentId, int roleId){
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
    }

    public String getName(){
        return name;
    }

    public int getDepartmentId(){
        return departmentId;
    }

    public int getRoleId(){
        return roleId;
    }
}
