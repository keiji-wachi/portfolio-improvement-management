package com.example.demo.Dto;

public class LoginResponseDto {
    private int id;
    private int department_id;
    private int role_id;
    private Boolean success;

    public LoginResponseDto(int id, int department_id, int role_id, Boolean success){
        this.id = id;
        this.department_id = department_id;
        this.role_id = role_id;
        this.success = success;
    }

    public int getId(){
        return id;
    }

    public int getDepartmentId(){
        return department_id;
    }

    public int getRoleId(){
        return role_id;
    }

    public boolean getSuccess(){
        return success;
    }

}
