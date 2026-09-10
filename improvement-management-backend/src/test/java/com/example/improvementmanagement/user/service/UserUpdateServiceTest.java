package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.common.exception.ResourceNotFoundException;
import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.dto.UserUpdateTargetDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;
import com.example.improvementmanagement.user.repository.UserUpdateRepository;

@ExtendWith(MockitoExtension.class)
class UserUpdateServiceTest {

    @Mock
    private UserUpdateRepository repository;

    @Mock
    private UserUpdateDto dto;

    @Mock
    private CreateUserRepository createUserRepository;

    @InjectMocks
    private UserUpdateService userUpdateService;

    //正常パターン

    @Test
    void 正常にユーザーを更新できる() {

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(repository.findById(1))
                .thenReturn(Optional.of(
                        new UserUpdateTargetDto(
                                1,
                                "EMP001",
                                "test",
                                1,
                                4
                        )
                ));

        when(createUserRepository.existsDepartmentById(1))
                .thenReturn(true);

        when(createUserRepository.existsRoleById(4))
                .thenReturn(true);

        when(repository.updateUser(1, dto))
                .thenReturn(1);

        userUpdateService.updateUser(1, dto);

        verify(repository)
                .updateUser(1, dto);
    }

    //異常パターン

    @Test
    void 存在しないユーザーを更新できない() {

        when(repository.findById(1))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userUpdateService.updateUser(1, dto)
        );

        assertEquals(
                "更新対象ユーザーが存在しません",
                exception.getMessage()
        );

        verify(repository, never())
                .updateUser(any(), any());
    }

    @Test
    void 存在しない部署へ変更できない() {

        when(dto.getDepartmentId()).thenReturn(1);

        when(repository.findById(1))
                .thenReturn(Optional.of(
                        new UserUpdateTargetDto(
                                1,
                                "EMP001",
                                "test",
                                1,
                                4
                        )
                ));

        when(createUserRepository.existsDepartmentById(1))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userUpdateService.updateUser(1, dto)
        );

        assertEquals(
                "指定された部署が存在しません",
                exception.getMessage()
        );

        verify(repository, never())
                .updateUser(any(), any());
    }

    @Test
    void 存在しない役職へ変更できない() {

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(repository.findById(1))
                .thenReturn(Optional.of(
                        new UserUpdateTargetDto(
                                1,
                                "EMP001",
                                "test",
                                1,
                                4
                        )
                ));

        when(createUserRepository.existsDepartmentById(1))
                .thenReturn(true);

        when(createUserRepository.existsRoleById(4))
                .thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userUpdateService.updateUser(1, dto)
        );

        assertEquals(
                "指定されたroleが存在しません",
                exception.getMessage()
        );

        verify(repository, never())
                .updateUser(any(), any());
    }
}