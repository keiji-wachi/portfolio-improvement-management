package com.example.demo.Dto;

public class DepartmentDto {
    private int departmentId;
    private String departmentName;

    public DepartmentDto(int departmentId, String departmentName){
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
    public int getDepartmentId(){
        return departmentId;
    }

    public String getDepartmentName(){
        return departmentName;
    }
}
