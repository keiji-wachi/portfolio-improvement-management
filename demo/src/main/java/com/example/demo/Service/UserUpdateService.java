package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Dto.UserUpdateTargetDto;
import com.example.demo.Repository.UserUpdateRepository;
import com.example.demo.Repository.CreateUserRepository;

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

    public void updateUser(LoginResponseDto loginUser, Integer id, UserUpdateDto dto) {
        UserUpdateTargetDto targetUser = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("更新対象ユーザーが存在しません"));

            int loginRoleId = loginUser.getRoleId();
            int requestedRoleId = dto.getRoleId();
            int targetRoleId = targetUser.getRoleId();

            if (loginRoleId == SYSTEM_ADMIN) {

                } else if (loginRoleId == INSTRUCTOR) {
                    if (!targetUser.getDepartmentId().equals(loginUser.getDepartmentId())) {
                        throw new IllegalStateException("他部署のユーザーは更新できません");
                    }

                    if (!dto.getDepartmentId().equals(loginUser.getDepartmentId())) {
                        throw new IllegalStateException("他部署へ変更することはできません");
                    }

                    if (requestedRoleId != RELIEF && requestedRoleId != WORKER) {
                        throw new IllegalStateException("指定されたroleへ変更する権限がありません");
                    }

                    if (targetRoleId != RELIEF && targetRoleId != WORKER) {
                        throw new IllegalStateException("このユーザーを更新する権限がありません");
                    }

                } else {
                    throw new IllegalStateException("ユーザー更新権限がありません");
                }
                
                if (!createUserRepository.existsDepartmentById(dto.getDepartmentId())) {
                    throw new IllegalArgumentException("指定された部署が存在しません");
                }

                if (!createUserRepository.existsRoleById(dto.getRoleId())) {
                    throw new IllegalArgumentException("指定されたroleが存在しません");
                }

                int updateCount = repository.updateUser(id, dto);

                if (updateCount != 1) {
                    throw new IllegalStateException("ユーザー更新に失敗しました");
                }
    }
}


