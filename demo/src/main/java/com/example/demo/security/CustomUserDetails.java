package com.example.demo.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private Integer userId;
    private String employeeNo;
    private Integer departmentId;
    private Integer roleId;
    private String password;
    private final boolean firstLoginFlag;

    public CustomUserDetails(Integer userId, String employeeNo, Integer departmentId, Integer roleId, String password, boolean firstLoginFlag) {
        this.userId = userId;
        this.employeeNo = employeeNo;
        this.departmentId = departmentId;
        this.roleId = roleId;
        this.password = password;
        this.firstLoginFlag = firstLoginFlag;
    }

    @Override
    public String getUsername() {
        return employeeNo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public boolean getFirstLoginFlag() {
        return firstLoginFlag;
    }
}