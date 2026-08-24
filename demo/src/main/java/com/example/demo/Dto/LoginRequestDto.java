package com.example.demo.Dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {

    private String employeeNo;
    private String password;

    public LoginRequestDto(String employeeNo, String password){
        this.employeeNo = employeeNo;
        this.password = password;
    }

    @NotBlank
    public String getEmployeeNo(){
        return employeeNo;
    }

    @NotBlank
    public String getPassWord(){
        return password;
    }
}
