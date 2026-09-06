package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.common.exception.DuplicateResourceException;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;
import com.example.improvementmanagement.user.dto.CreateUserDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;

@ExtendWith(MockitoExtension.class)
class UserCreateServiceTest {

    @Mock
    private CreateUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CreateUserDto dto;

    @Mock
    private CustomUserDetails loginUser;

    @InjectMocks
    private UserCreateService userCreateService;

    //正常パターン

    @Test
    void システム管理者はユーザーを登録できる() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(1);
        when(dto.getRole_id()).thenReturn(4);
        when(dto.getPassword()).thenReturn("password123");

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(false);

        when(repository.existsDepartmentById(1))
                .thenReturn(true);

        when(repository.existsRoleById(4))
                .thenReturn(true);

        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedPassword");

        when(repository.createUser(dto, "hashedPassword"))
                .thenReturn(1);

        int result = userCreateService.createUser(dto, loginUser);

        assertEquals(1, result);

        verify(repository)
                .createUser(dto, "hashedPassword");
    }

    @Test 
    void 指導員は自部署のリリーフユーザーの作成可能(){

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(1);
        when(dto.getRole_id()).thenReturn(3);
        when(dto.getPassword()).thenReturn("password123");

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(false);

        when(repository.existsDepartmentById(1))
                .thenReturn(true);

        when(repository.existsRoleById(3))
                .thenReturn(true);

        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedPassword");

        when(repository.createUser(dto, "hashedPassword"))
                .thenReturn(1);

        int result = userCreateService.createUser(dto, loginUser);

        assertEquals(1, result);

        verify(repository)
                .createUser(dto, "hashedPassword");
    }

    @Test 
    void 指導員は自部署の作業者ユーザーの作成可能(){

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(1);
        when(dto.getRole_id()).thenReturn(4);
        when(dto.getPassword()).thenReturn("password123");

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(false);

        when(repository.existsDepartmentById(1))
                .thenReturn(true);

        when(repository.existsRoleById(4))
                .thenReturn(true);

        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedPassword");

        when(repository.createUser(dto, "hashedPassword"))
                .thenReturn(1);

        int result = userCreateService.createUser(dto, loginUser);

        assertEquals(1, result);

        verify(repository)
                .createUser(dto, "hashedPassword");
    }

    //異常パターン
    @Test 
    void 指導員は他部署のユーザーを登録ができない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartment_id()).thenReturn(2);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "他部署のユーザーは登録できません",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test 
    void 指導員はシステム管理者ロールを登録ができない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getRole_id()).thenReturn(1);
        when(dto.getDepartment_id()).thenReturn(1);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "指導員が登録できるのはリリーフまたは作業者のみです",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test 
    void 指導員は指導員ロールを登録ができない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getRole_id()).thenReturn(2);
        when(dto.getDepartment_id()).thenReturn(1);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "指導員が登録できるのはリリーフまたは作業者のみです",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test 
    void リリーフはユーザーを登録ができない() {

        when(loginUser.getRoleId()).thenReturn(3);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "ユーザーを登録する権限がありません",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test
    void 作業者はユーザーを登録ができない() {

        when(loginUser.getRoleId()).thenReturn(4);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "ユーザーを登録する権限がありません",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test 
    void 社員番号重複時にユーザー登録ができない() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getEmployeeNo()).thenReturn("E001");

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(true);

        DuplicateResourceException exception = assertThrows(
            DuplicateResourceException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "この社員番号はすでに登録されています",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test 
    void 存在しない部署のユーザー登録ができない() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(99);

        when(repository.existsDepartmentById(99)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "指定された部署は存在しません",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());
    }

    @Test 
    void 存在しないロールのユーザー登録ができない() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(1);
        when(dto.getRole_id()).thenReturn(99);

        when(repository.existsDepartmentById(1)).thenReturn(true);

        when(repository.existsRoleById(99)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> userCreateService.createUser(dto, loginUser)
        );

        assertEquals(
            "指定された役職は存在しません",
            exception.getMessage()
        );

        verify(repository, never()).
                createUser(any(), anyString());

    }
}
