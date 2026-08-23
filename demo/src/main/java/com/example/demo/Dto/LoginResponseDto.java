package com.example.demo.Dto;

public class LoginResponseDto {
    private int id;
    private Integer department_id;
    private Integer role_id;
    private Boolean success;

    public LoginResponseDto(int id, Integer department_id, Integer role_id, Boolean success){
        this.id = id;
        this.department_id = department_id;
        this.role_id = role_id;
        this.success = success;
    }

    public int getId(){
        return id;
    }

    public Integer getDepartmentId(){
        return department_id;
    }

    public Integer getRoleId(){
        return role_id;
    }

    public boolean getSuccess(){
        return success;
    }

}
