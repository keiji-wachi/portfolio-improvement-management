package com.example.improvementmanagement.auth.dto;

public class LoginResponseDto {

    private Integer userId;
    private String name;

    private Integer departmentId;
    private String departmentName;

    private Integer roleId;

    private boolean firstLoginFlag;
    private boolean success;

    public LoginResponseDto(
            Integer userId,
            String name,
            Integer departmentId,
            String departmentName,
            Integer roleId,
            boolean firstLoginFlag,
            boolean success) {

        this.userId = userId;
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.roleId = roleId;
        this.firstLoginFlag = firstLoginFlag;
        this.success = success;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public boolean getFirstLoginFlag() {
        return firstLoginFlag;
    }

    public boolean getSuccess() {
        return success;
    }
}