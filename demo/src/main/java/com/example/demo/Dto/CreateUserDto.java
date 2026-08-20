package com.example.demo.Dto;

public class CreateUserDto {

    private String name; 
    private int department_id;
    private int role_id;
    private String password;

    public CreateUserDto(String name, int department_id, int role_id, String password){
        this.name = name;
        this.department_id = department_id;
        this.role_id = role_id;
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public int getDepartment_id(){
        return department_id;
    }

    public int getRole_id(){
        return role_id;
    }

   public String getPassword(){
        return password;
    }
}
