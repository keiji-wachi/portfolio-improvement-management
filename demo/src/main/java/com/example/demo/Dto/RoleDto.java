package com.example.demo.Dto;

public class RoleDto {
    private int roleId;
    private String roleName;

    public RoleDto(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }
    public int getRoleId(){
        return roleId;
    }

    public String getRoleName(){
        return roleName;
    }
}
