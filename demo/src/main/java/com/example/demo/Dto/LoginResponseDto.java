package com.example.demo.Dto;

public class LoginResponseDto {
    private Integer userId;
    private Integer departmentId;
    private Integer roleId;
    private boolean firstLoginFlag;
    private boolean success;

    public LoginResponseDto(Integer userId, Integer departmentId, Integer roleId, boolean firstLoginFlag, boolean success){
        this.userId = userId;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.firstLoginFlag = firstLoginFlag;
        this.success = success;
    }

    public Integer getUserId(){
        return userId;
    }

    public Integer getDepartmentId(){
        return departmentId;
    }

    public Integer getRoleId(){
        return roleId;
    }

    public boolean getFirstLoginFlag(){
        return firstLoginFlag;
    }

    public boolean getSuccess(){
        return success;
    }

}
