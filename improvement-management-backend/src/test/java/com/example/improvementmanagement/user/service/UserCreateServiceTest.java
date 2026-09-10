package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.improvementmanagement.common.exception.DuplicateResourceException;
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

    @InjectMocks
    private UserCreateService userCreateService;


    //正常パターン

    @Test
    void 正常にユーザーを登録できる() {

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

        int result = userCreateService.createUser(dto);

        assertEquals(1, result);

        verify(repository)
                .createUser(dto, "hashedPassword");
    }

    //異常パターン

    @Test
    void 社員番号重複時にユーザー登録ができない() {

        when(dto.getEmployeeNo()).thenReturn("E001");

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(true);

        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> userCreateService.createUser(dto)
        );

        assertEquals(
                "この社員番号はすでに登録されています",
                exception.getMessage()
        );

        verify(repository, never())
                .createUser(any(), anyString());
    }

    @Test
    void 存在しない部署のユーザー登録ができない() {

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(99);

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(false);

        when(repository.existsDepartmentById(99))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userCreateService.createUser(dto)
        );

        assertEquals(
                "指定された部署は存在しません",
                exception.getMessage()
        );

        verify(repository, never())
                .createUser(any(), anyString());
    }

    @Test
    void 存在しないロールのユーザー登録ができない() {

        when(dto.getEmployeeNo()).thenReturn("E001");
        when(dto.getDepartment_id()).thenReturn(1);
        when(dto.getRole_id()).thenReturn(99);

        when(repository.existsByEmployeeNo("E001"))
                .thenReturn(false);

        when(repository.existsDepartmentById(1))
                .thenReturn(true);

        when(repository.existsRoleById(99))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userCreateService.createUser(dto)
        );

        assertEquals(
                "指定された役職は存在しません",
                exception.getMessage()
        );

        verify(repository, never())
                .createUser(any(), anyString());
    }
}