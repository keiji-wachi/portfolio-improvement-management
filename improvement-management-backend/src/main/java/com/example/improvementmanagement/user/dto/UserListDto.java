package com.example.improvementmanagement.user.dto;

public class UserListDto {
    private int id;
    private String employeeNumber;
    private String name; 
    private int departmentId;
    private int roleId;
    private String departmentName;
    private String roleName;

    public UserListDto(int id, String employeeNumber, String name, int departmentId, int roleId, String departmentName, String roleName){
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.departmentName = departmentName;
        this.roleName = roleName;
    }

    public int getId(){
        return id;
    }

    public String getEmployeeNumber(){
        return employeeNumber;
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

    public String getDepartmentName(){
        return departmentName;
    }

    public String getRoleName(){
        return roleName;
    }

}
