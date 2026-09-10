package com.example.improvementmanagement.user.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;
import com.example.improvementmanagement.user.repository.UserUpdateRepository;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;

@Service
public class UserUpdateService {

    private final UserUpdateRepository repository;
    private final CreateUserRepository createUserRepository;

    public UserUpdateService(UserUpdateRepository repository, CreateUserRepository createUserRepository) {
        this.repository = repository;
        this.createUserRepository = createUserRepository;
    }

    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'INSTRUCTOR') " + "and @userAuthorization.canUpdate(authentication, #id, #dto)")
    public void updateUser(Integer id, UserUpdateDto dto) {

                repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("更新対象ユーザーが存在しません"));

                if (!createUserRepository.existsDepartmentById(dto.getDepartmentId())) {
                    throw new ResourceNotFoundException("指定された部署が存在しません");
                }

                if (!createUserRepository.existsRoleById(dto.getRoleId())) {
                    throw new ResourceNotFoundException("指定されたroleが存在しません");
                }

                int updateCount = repository.updateUser(id, dto);

                if (updateCount != 1) {
                    throw new RuntimeException("ユーザー更新に失敗しました");
                }
    }
}


