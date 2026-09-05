package com.example.improvementmanagement.auth.dto;

public class LoginUserDto {
    private int userId;
    private String employeeNo;
    private String passwordHash;
    private int departmentId;
    private int roleId;
    private boolean firstLoginFlag;

    public LoginUserDto(int userId, String employeeNo, String passwordHash, int departmentId, int roleId, boolean firstLoginFlag){
        this.userId = userId;
        this.employeeNo = employeeNo;
        this.passwordHash = passwordHash;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.firstLoginFlag = firstLoginFlag;
    }

    public int getUserId(){
        return userId;
    }

    public int getDepartmentId(){
        return departmentId;
    }

    public int getRoleId(){
        return roleId;
    }

    public boolean getFirstLoginFlag(){
        return firstLoginFlag;
    }

    public String getEmployeeNo(){
        return employeeNo;
    }

    public String getPasswordHash(){
        return passwordHash;
    }


}
