package com.example.improvementmanagement.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.UserListDto;
import com.example.improvementmanagement.user.repository.UserListRepository;

@Service
public class UserListService {

    private static final int SYSTEM_ADMIN = 1;
    private static final int INSTRUCTOR = 2;

    private final UserListRepository userListRepository;

    public UserListService(UserListRepository userListRepository) {
        this.userListRepository = userListRepository;
    }

    public List<UserListDto> findAll(CustomUserDetails loginUser) {

        int roleId = loginUser.getRoleId();

        if (roleId == SYSTEM_ADMIN) {
            return userListRepository.findAll();
        }

        if (roleId == INSTRUCTOR) {
            return userListRepository.findByDepartmentId(
                loginUser.getDepartmentId()
            );
        }

        throw new RuntimeException("ユーザー一覧を参照する権限がありません");
    }
}