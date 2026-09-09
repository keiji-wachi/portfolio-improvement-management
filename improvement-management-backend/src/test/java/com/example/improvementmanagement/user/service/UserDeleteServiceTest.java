package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.example.improvementmanagement.user.dto.UserDeleteTargetDto;
import com.example.improvementmanagement.user.repository.UserDeleteRepository;

@ExtendWith(MockitoExtension.class)
public class UserDeleteServiceTest {

    @InjectMocks
    private UserDeleteService userDeleteService;

    @Mock
    private CustomUserDetails loginUser;

    @Mock
    private UserDeleteTargetDto dto;

    @Mock
    private UserDeleteRepository repository;


    //正常パターン

    @Test
    void システム管理者はユーザーを削除できる() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(dto.getId()).thenReturn(1);

        when(repository.findById(1))
                .thenReturn(dto);

        when(repository.logicalDelete(1))
                .thenReturn(1);

        int result = userDeleteService.deleteUser(1, loginUser);
        assertEquals(1, result);

        verify(repository).logicalDelete(1);

    }

    @Test 
    void 指導員は自部署のリリーフユーザーの削除可能() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getId()).thenReturn(2);
        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(3);

        when(repository.findById(2))
                .thenReturn(dto);

        when(repository.logicalDelete(2))
                .thenReturn(1);

        int result = userDeleteService.deleteUser(2, loginUser);
        assertEquals(1, result);

        verify(repository).logicalDelete(2);
    }

    @Test 
    void 指導員は自部署の作業者ユーザーの削除可能() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getId()).thenReturn(3);
        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(4);

        when(repository.findById(3))
                .thenReturn(dto);

        when(repository.logicalDelete(3))
                .thenReturn(1);

        int result = userDeleteService.deleteUser(3, loginUser);
        assertEquals(1, result);

        verify(repository).logicalDelete(3);
    }

    //異常パターン

    @Test 
    void 指導員は他部署のユーザーを削除できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getId()).thenReturn(4);
        when(dto.getDepartmentId()).thenReturn(2);

        when(repository.findById(4))
                .thenReturn(dto);
        
        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(4, loginUser)
        );

        assertEquals(
            "他部署のユーザーは削除できません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }

    @Test
    void 指導員はシステム管理者ユーザーを削除できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getId()).thenReturn(5);
        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(1);

        when(repository.findById(5))
                .thenReturn(dto);
        
        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(5, loginUser)
        );

        assertEquals(
            "このユーザーは削除できません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }

    @Test 
    void 指導員は指導員ユーザーを削除できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(dto.getId()).thenReturn(6);
        when(dto.getDepartmentId()).thenReturn(1);
        when(dto.getRoleId()).thenReturn(2);

        when(repository.findById(6))
                .thenReturn(dto);
        
        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(6, loginUser)
        );

        assertEquals(
            "このユーザーは削除できません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    
    }

    @Test 
    void システム管理者は自分自身を削除できない() {

        when(loginUser.getRoleId()).thenReturn(1);
        when(loginUser.getUserId()).thenReturn(7);

        when(dto.getId()).thenReturn(7);

        when(repository.findById(7))
                .thenReturn(dto);
        
        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(7, loginUser)
        );

        assertEquals(
            "このユーザーは削除することはできません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());

    }

    @Test
    void 指導員は自分自身を削除できない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getUserId()).thenReturn(8);

        when(dto.getId()).thenReturn(8);

        when(repository.findById(8))
                .thenReturn(dto);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(8, loginUser)
        );

        assertEquals(
            "このユーザーは削除することはできません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }

    @Test 
    void リリーフはユーザー削除できない() {

        when(loginUser.getRoleId()).thenReturn(3);
        when(dto.getId()).thenReturn(9);

        when(repository.findById(9))
                .thenReturn(dto);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(9, loginUser)
        );

        assertEquals(
            "ユーザーを削除する権限がありません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }

    @Test
    void 作業者はユーザー削除できない() {

        when(loginUser.getRoleId()).thenReturn(4);
        when(dto.getId()).thenReturn(10);

        when(repository.findById(10))
                .thenReturn(dto);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
            () -> userDeleteService.deleteUser(10, loginUser)
        );

        assertEquals(
            "ユーザーを削除する権限がありません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }

    @Test 
    void 存在しないユーザーを削除できない() {
        when(repository.findById(11))
                .thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> userDeleteService.deleteUser(11, loginUser)
        );

        assertEquals(
            "削除対象ユーザーが存在しません",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }

    @Test   
    void 不正なユーザーIDで削除できない() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> userDeleteService.deleteUser(-1, loginUser)
        );  

        assertEquals(
            "削除対象ユーザーIDが不正です",
            exception.getMessage()
        );

        verify(repository, never()).
                logicalDelete(any());
    }
}
