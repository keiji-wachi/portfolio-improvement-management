package com.example.improvementmanagement.auth.security.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import static com.example.improvementmanagement.auth.security.constants.RoleConstants.*;

@Component("incidentAuthorization")
public class IncidentAuthorization {

    public boolean canCreate(Authentication authentication) {

        CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();

        return loginUser.getRoleId() == INSTRUCTOR || loginUser.getRoleId() == RELIEF;
    }

    public boolean canGet(Authentication authentication) {

        CustomUserDetails loginUser = (CustomUserDetails) authentication.getPrincipal();

        return loginUser.getRoleId() == INSTRUCTOR || loginUser.getRoleId() == RELIEF || loginUser.getRoleId() == WORKER;
    }
}