package com.example.improvementmanagement.user.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.UserListDto;
import com.example.improvementmanagement.user.repository.UserListRepository;
import static com.example.improvementmanagement.auth.security.constants.RoleConstants.*;

@Service
public class UserListService {

    private final UserListRepository userListRepository;

    public UserListService(UserListRepository userListRepository) {
        this.userListRepository = userListRepository;
    }

    @PreAuthorize("@userAuthorization.canGet(authentication)")
    public List<UserListDto> findAll(CustomUserDetails loginUser) {

        if (loginUser.getRoleId() == SYSTEM_ADMIN) {
            return userListRepository.findAll();
        }
        
        return userListRepository.findByDepartmentId(
            loginUser.getDepartmentId()
        );
    }
}