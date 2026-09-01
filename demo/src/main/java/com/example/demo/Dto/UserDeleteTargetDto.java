package com.example.demo.Dto;

public class UserDeleteTargetDto {

    private Integer id;
    private Integer departmentId;
    private Integer roleId;

    public UserDeleteTargetDto(
            Integer id,
            Integer departmentId,
            Integer roleId) {

        this.id = id;
        this.departmentId = departmentId;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public Integer getRoleId() {
        return roleId;
    }
}