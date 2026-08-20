package com.example.demo.Dto;

public class CRUDDto {

    private int id;
    private String name;

    public CRUDDto(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

}
