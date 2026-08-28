package com.example.demo.Dto;

public class UserUpdateTargetDto {

    private Integer id;
    private String employeeNo;
    private String name;
    private Integer departmentId;
    private Integer roleId;

    public UserUpdateTargetDto(Integer id, String employeeNo, String name, Integer departmentId, Integer roleId) {
        this.id = id;
        this.employeeNo = employeeNo;
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public String getName() {
        return name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

}
