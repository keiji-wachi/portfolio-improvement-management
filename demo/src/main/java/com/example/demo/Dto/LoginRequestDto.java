package com.example.demo.Dto;

public class LoginRequestDto {

    private int id;
    private String password;

    public LoginRequestDto(int id, String password){
        this.id = id;
        this.password = password;
    }

    public int getId(){
        return id;
    }

    public String getPassWord(){
        return password;
    }
}
