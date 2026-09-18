package com.example.improvementmanagement.auth.dto;

public class LoginUserDto {

    private int userId;
    private String employeeNo;
    private String name;
    private String passwordHash;

    private int departmentId;
    private String departmentName;

    private int roleId;
    private boolean firstLoginFlag;

    public LoginUserDto(
            int userId,
            String employeeNo,
            String name,
            String passwordHash,
            int departmentId,
            String departmentName,
            int roleId,
            boolean firstLoginFlag) {

        this.userId = userId;
        this.employeeNo = employeeNo;
        this.name = name;
        this.passwordHash = passwordHash;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.roleId = roleId;
        this.firstLoginFlag = firstLoginFlag;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getRoleId() {
        return roleId;
    }

    public boolean getFirstLoginFlag() {
        return firstLoginFlag;
    }
}