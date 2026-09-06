package com.example.improvementmanagement.user.service;

import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.dto.UserUpdateTargetDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;
import com.example.improvementmanagement.user.repository.UserUpdateRepository;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;

@Service
public class UserUpdateService {

    private final UserUpdateRepository repository;
    private final CreateUserRepository createUserRepository;
    private static final int SYSTEM_ADMIN = 1;
    private static final int INSTRUCTOR = 2;
    private static final int RELIEF = 3;
    private static final int WORKER = 4;

    public UserUpdateService(UserUpdateRepository repository, CreateUserRepository createUserRepository) {
        this.repository = repository;
        this.createUserRepository = createUserRepository;
    }

    public void updateUser(CustomUserDetails loginUser, Integer id, UserUpdateDto dto) {
        UserUpdateTargetDto targetUser = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("更新対象ユーザーが存在しません"));

            int loginRoleId = loginUser.getRoleId();
            int requestedRoleId = dto.getRoleId();
            int targetRoleId = targetUser.getRoleId();

            if (loginRoleId == SYSTEM_ADMIN) {

                } else if (loginRoleId == INSTRUCTOR) {
                    if (!targetUser.getDepartmentId().equals(loginUser.getDepartmentId())) {
                        throw new ForbiddenOperationException("他部署のユーザーは更新できません");
                    }

                    if (!dto.getDepartmentId().equals(loginUser.getDepartmentId())) {
                        throw new ForbiddenOperationException("他部署へ変更することはできません");
                    }

                    if (requestedRoleId != RELIEF && requestedRoleId != WORKER) {
                        throw new ForbiddenOperationException("指定されたroleへ変更する権限がありません");
                    }

                    if (targetRoleId != RELIEF && targetRoleId != WORKER) {
                        throw new ForbiddenOperationException("このユーザーを更新する権限がありません");
                    }

                } else {
                    throw new ForbiddenOperationException("ユーザー更新権限がありません");
                }
                
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


