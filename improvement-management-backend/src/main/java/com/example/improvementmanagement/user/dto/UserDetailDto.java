package com.example.improvementmanagement.user.dto;

public class UserDetailDto {

    private Integer id;
    private String employeeNumber;
    private String name;
    private Integer departmentId;
    private Integer roleId;

    public UserDetailDto(
            Integer id,
            String employeeNumber,
            String name,
            Integer departmentId,
            Integer roleId) {

        this.id = id;
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.departmentId = departmentId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
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