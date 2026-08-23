package com.example.demo.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.CreateUserDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Repository.CreateUserRepository;

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

    public int createUser(
            CreateUserDto dto,
            LoginResponseDto loginUser) {

        // 未ログインなら拒否
        if (loginUser == null) {
            throw new RuntimeException("ログインが必要です");
        }

        int roleId = loginUser.getRoleId();

        // ユーザー作成可能なroleだけ許可
        if (roleId != SYSTEM_ADMIN_ROLE_ID
                && roleId != INSTRUCTOR_ROLE_ID) {

            throw new RuntimeException(
                    "ユーザーを登録する権限がありません"
            );
        }

            // Step 8：指導員の部署スコープ
        if (roleId == INSTRUCTOR_ROLE_ID) {

            if (!loginUser.getDepartmentId().equals(dto.getDepartment_id())) {
            throw new RuntimeException("他部署のユーザーは登録できません");
        }
            // Step 9：指導員のroleスコープ
            int targetRoleId = dto.getRole_id();

            if (targetRoleId != RELIEF_ROLE_ID
                && targetRoleId != WORKER_ROLE_ID) {
                    throw new RuntimeException("指導員が登録できるのはリリーフまたは作業者のみです");
            }
        }

            // Step 10：社員番号重複チェック
        if (repository.existsByEmployeeNo(dto.getEmployeeNo())) {
                throw new RuntimeException("この社員番号はすでに登録されています");
        }

            // Step 11-1：department実在チェック
        if (!repository.existsDepartmentById(dto.getDepartment_id())) {
                throw new RuntimeException("指定された部署は存在しません");
        }

        // Step 11-2：role実在チェック
        if (!repository.existsRoleById(dto.getRole_id())) {
            throw new RuntimeException("指定された役職は存在しません");
        }
    

        // Step 13 passwordハッシュ化
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        return repository.createUser(dto, passwordHash);
    }
}