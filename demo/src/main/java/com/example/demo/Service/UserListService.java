package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.UserListDto;
import com.example.demo.Repository.UserListRepository;

@Service
public class UserListService {

    private static final int SYSTEM_ADMIN = 1;
    private static final int INSTRUCTOR = 2;

    private final UserListRepository userListRepository;

    public UserListService(UserListRepository userListRepository) {
        this.userListRepository = userListRepository;
    }

    public List<UserListDto> findAll(LoginResponseDto loginUser) {

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