package com.example.demo.Dto;

public class UserListDto {
    private int id;
    private String name; 
    private int departmentId;
    private int roleId;
    private String departmentName;
    private String roleName;

    public UserListDto(int id, String name, int departmentId, int roleId, String departmentName, String roleName){
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.departmentName = departmentName;
        this.roleName = roleName;
    }

    public int getId(){
        return id;
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
