package com.example.improvementmanagement.user.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.user.dto.CreateUserDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;
import com.example.improvementmanagement.common.exception.DuplicateResourceException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;

@Service
public class UserCreateService {

    private final CreateUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserCreateService(CreateUserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize ("hasAnyRole('SYSTEM_ADMIN', 'INSTRUCTOR')" + " and @userAuthorization.canCreate(authentication, #dto)")
    public int createUser(CreateUserDto dto) {

        if (repository.existsByEmployeeNo(dto.getEmployeeNo())) {
                throw new DuplicateResourceException("この社員番号はすでに登録されています");
        }

        if (!repository.existsDepartmentById(dto.getDepartment_id())) {
                throw new ResourceNotFoundException("指定された部署は存在しません");
        }

        if (!repository.existsRoleById(dto.getRole_id())) {
            throw new ResourceNotFoundException("指定された役職は存在しません");
        }
    
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        return repository.createUser(dto, passwordHash);
    }
}