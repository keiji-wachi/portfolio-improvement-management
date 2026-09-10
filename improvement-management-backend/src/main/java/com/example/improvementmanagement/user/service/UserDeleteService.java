package com.example.improvementmanagement.user.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.user.dto.UserDeleteTargetDto;
import com.example.improvementmanagement.user.repository.UserDeleteRepository;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;


@Service
public class UserDeleteService {

    private final UserDeleteRepository userDeleteRepository;

    public UserDeleteService(UserDeleteRepository userDeleteRepository) {
        this.userDeleteRepository = userDeleteRepository;
    }

    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'INSTRUCTOR') " + "and @userAuthorization.canDelete(authentication, #id)")
    public int deleteUser(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("削除対象ユーザーIDが不正です");
        }

        UserDeleteTargetDto targetUser = userDeleteRepository.findById(id);

        if (targetUser == null) {
            throw new ResourceNotFoundException("削除対象ユーザーが存在しません");
        }
            return userDeleteRepository.logicalDelete(id);
    }
}