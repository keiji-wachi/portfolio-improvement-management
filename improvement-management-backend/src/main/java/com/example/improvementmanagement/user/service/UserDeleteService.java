package com.example.improvementmanagement.user.service;

import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.UserDeleteTargetDto;
import com.example.improvementmanagement.user.repository.UserDeleteRepository;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;


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
            throw new ResourceNotFoundException("削除対象ユーザーが存在しません");
        }

        int roleId = loginUser.getRoleId();

        if (loginUser.getUserId().equals(targetUser.getId())) {
            throw new ForbiddenOperationException("このユーザーは削除することはできません");
        }

        if (roleId == SYSTEM_ADMIN) {
            return userDeleteRepository.logicalDelete(id);
        }

        if (roleId == INSTRUCTOR) {

            if (!loginUser.getDepartmentId().equals(targetUser.getDepartmentId())) {
                throw new ForbiddenOperationException("他部署のユーザーは削除できません");
            }

            int targetRoleId = targetUser.getRoleId();

            if (targetRoleId != RELIEF && targetRoleId != WORKER) {
                throw new ForbiddenOperationException("このユーザーは削除できません");
            }

            return userDeleteRepository.logicalDelete(id);
        }

        throw new ForbiddenOperationException("ユーザーを削除する権限がありません");
    }
}