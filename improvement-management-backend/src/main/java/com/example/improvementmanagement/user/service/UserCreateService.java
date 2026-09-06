package com.example.improvementmanagement.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.CreateUserDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;
import com.example.improvementmanagement.common.exception.DuplicateResourceException;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;

@Service
public class UserCreateService {

    private static final int SYSTEM_ADMIN_ROLE_ID = 1;
    private static final int INSTRUCTOR_ROLE_ID = 2;
    private static final int RELIEF_ROLE_ID = 3;
    private static final int WORKER_ROLE_ID = 4;

    private final CreateUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateService(CreateUserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public int createUser(CreateUserDto dto,CustomUserDetails loginUser) {
        
        int roleId = loginUser.getRoleId();

        // ユーザー作成可能なroleだけ許可
        if (roleId != SYSTEM_ADMIN_ROLE_ID
                && roleId != INSTRUCTOR_ROLE_ID) {

            throw new ForbiddenOperationException("ユーザーを登録する権限がありません");
        }

            // Step 8：指導員の部署スコープ
        if (roleId == INSTRUCTOR_ROLE_ID) {

            if (loginUser.getDepartmentId() != (dto.getDepartment_id())) {
            throw new ForbiddenOperationException("他部署のユーザーは登録できません");
        }
            // Step 9：指導員のroleスコープ
            int targetRoleId = dto.getRole_id();

            if (targetRoleId != RELIEF_ROLE_ID
                && targetRoleId != WORKER_ROLE_ID) {
                    throw new ForbiddenOperationException("指導員が登録できるのはリリーフまたは作業者のみです");
            }
        }

            // Step 10：社員番号重複チェック
        if (repository.existsByEmployeeNo(dto.getEmployeeNo())) {
                throw new DuplicateResourceException("この社員番号はすでに登録されています");
        }

            // Step 11-1：department実在チェック
        if (!repository.existsDepartmentById(dto.getDepartment_id())) {
                throw new ResourceNotFoundException("指定された部署は存在しません");
        }

        // Step 11-2：role実在チェック
        if (!repository.existsRoleById(dto.getRole_id())) {
            throw new ResourceNotFoundException("指定された役職は存在しません");
        }
    

        // Step 13 passwordハッシュ化
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        return repository.createUser(dto, passwordHash);
    }
}