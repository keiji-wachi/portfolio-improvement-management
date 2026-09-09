package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;
import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.dto.UserUpdateTargetDto;
import com.example.improvementmanagement.user.repository.CreateUserRepository;
import com.example.improvementmanagement.user.repository.UserUpdateRepository;

@ExtendWith(MockitoExtension.class)
public class UserUpdateServiceTest {
    
    @Mock
    private UserUpdateRepository repository;

    @Mock
    private UserUpdateDto dto;

    @Mock
    private CustomUserDetails loginUser;

    @Mock
    private CreateUserRepository createUserRepository;

    @InjectMocks
    private UserUpdateService userUpdateService;

    //正常パターン

    @Test
    void システム管理者はユーザーを更新できる() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(createUserRepository.existsDepartmentById(1))
            .thenReturn(true);

        when(createUserRepository.existsRoleById(4))
        .thenReturn(true);

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

        when(repository.updateUser(1, dto)).thenReturn(1);

        userUpdateService.updateUser(loginUser,1, dto);

        verify(repository).updateUser(1, dto);
    }

    @Test
    void 指導員は自部署のユーザーを更新できる() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(createUserRepository.existsDepartmentById(1))
            .thenReturn(true);

        when(createUserRepository.existsRoleById(4))
        .thenReturn(true);

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

        when(repository.updateUser(1, dto)).thenReturn(1);

        userUpdateService.updateUser(loginUser,1, dto);

        verify(repository).updateUser(1, dto);
    }

    //異常パターン

    @Test
    void 指導員は他部署のユーザーを更新できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(repository.findById(1))
            .thenReturn(Optional.of(
                new UserUpdateTargetDto(
                1,
                "EMP001",
                "test",
                2,
                4
            )
        ));

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("他部署のユーザーは更新できません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test
    void 指導員は自部署ユーザーを他部署へ変更できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(2);
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

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("他部署へ変更することはできません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test 
    void 指導員は自部署ユーザーをシステム管理者へ変更できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(1);

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

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("指定されたroleへ変更する権限がありません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test
    void 指導員は自部署ユーザーを指導員へ変更できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(2);

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

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("指定されたroleへ変更する権限がありません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test 
    void 指導員はシステム管理者を更新できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(repository.findById(1))
            .thenReturn(Optional.of(
                new UserUpdateTargetDto(
                1,
                "EMP001",
                "test",
                1,
                1
            )
        ));

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("このユーザーを更新する権限がありません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test 
    void 指導員は指導員を更新できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(repository.findById(1))
            .thenReturn(Optional.of(
                new UserUpdateTargetDto(
                1,
                "EMP001",
                "test",
                1,
                2
            )
        ));

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("このユーザーを更新する権限がありません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test
    void リリーフはユーザーを更新できない() {

        when(loginUser.getRoleId()).thenReturn(3);

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

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("ユーザー更新権限がありません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test
    void 作業者はユーザーを更新できない() {

        when(loginUser.getRoleId()).thenReturn(4);

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

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("ユーザー更新権限がありません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test 
    void 存在しないユーザーを更新できない() {

        when(repository.findById(1))
            .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("更新対象ユーザーが存在しません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test
    void 存在しない部署へ変更できない() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(createUserRepository.existsDepartmentById(1))
            .thenReturn(false);

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

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("指定された部署が存在しません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }

    @Test
    void 存在しない役職へ変更できない() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(createUserRepository.existsDepartmentById(1))
            .thenReturn(true);

        when(createUserRepository.existsRoleById(4))
            .thenReturn(false);

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

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userUpdateService.updateUser(loginUser,1, dto);
        });

        assertEquals("指定されたroleが存在しません", exception.getMessage());
        verify(repository, never()).updateUser(any(), any());
    }
}