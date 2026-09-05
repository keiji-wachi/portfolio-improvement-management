package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserDeleteTargetDto;
import com.example.demo.Repository.UserDeleteRepository;
import com.example.demo.security.CustomUserDetails;

@Service
public class UserDeleteService {

    private static final int SYSTEM_ADMIN = 1;
    private static final int INSTRUCTOR = 2;
    private static final int RELIEF = 3;
    private static final int WORKER = 4;

    private final UserDeleteRepository userDeleteRepository;

    public UserDeleteService(UserDeleteRepository userDeleteRepository) {
        this.userDeleteRepository = userDeleteRepository;
    }

    public int deleteUser(Integer id, CustomUserDetails loginUser) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("削除対象ユーザーIDが不正です");
        }

        UserDeleteTargetDto targetUser = userDeleteRepository.findById(id);

        if (targetUser == null) {
            throw new IllegalArgumentException("削除対象ユーザーが存在しません");
        }

        int roleId = loginUser.getRoleId();

        if (loginUser.getUserId().equals(targetUser.getId())) {
            throw new RuntimeException("このユーザーは削除することはできません");
        }

        if (roleId == SYSTEM_ADMIN) {
            return userDeleteRepository.logicalDelete(id);
        }

        if (roleId == INSTRUCTOR) {

            if (!loginUser.getDepartmentId().equals(targetUser.getDepartmentId())) {
                throw new RuntimeException("他部署のユーザーは削除できません");
            }

            int targetRoleId = targetUser.getRoleId();

            if (targetRoleId != RELIEF && targetRoleId != WORKER) {
                throw new RuntimeException("このユーザーは削除できません");
            }

            return userDeleteRepository.logicalDelete(id);
        }

        throw new RuntimeException("ユーザーを削除する権限がありません");
    }
}