package com.example.improvementmanagement.auth.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private Integer userId;
    private String employeeNo;
    private String name;

    private Integer departmentId;
    private String departmentName;

    private Integer roleId;
    private String password;

    private final boolean firstLoginFlag;

    public CustomUserDetails(
            Integer userId,
            String employeeNo,
            String name,
            Integer departmentId,
            String departmentName,
            Integer roleId,
            String password,
            boolean firstLoginFlag) {

        this.userId = userId;
        this.employeeNo = employeeNo;
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
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

        String roleName = switch (roleId) {
            case 1 -> "ROLE_SYSTEM_ADMIN";
            case 2 -> "ROLE_INSTRUCTOR";
            case 3 -> "ROLE_RELIEF";
            case 4 -> "ROLE_WORKER";

            default ->
                throw new IllegalStateException(
                    "不正なロールIDです: " + roleId
                );
        };

        return List.of(
            new SimpleGrantedAuthority(roleName)
        );
    }

    public Integer getUserId() {
        return userId;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public boolean getFirstLoginFlag() {
        return firstLoginFlag;
    }
}