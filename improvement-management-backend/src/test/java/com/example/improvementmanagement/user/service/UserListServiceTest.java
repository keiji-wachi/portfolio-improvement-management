package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.UserListDto;
import com.example.improvementmanagement.user.repository.UserListRepository;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;



@ExtendWith(MockitoExtension.class)
public class UserListServiceTest {

    @InjectMocks
    private UserListService userListService;

    @Mock
    private CustomUserDetails loginUser;

    @Mock
    private UserListDto dto;

    @Mock
    private UserListRepository repository;

    //正常パターン

    @Test
    void システム管理者はユーザー一覧を取得できる() {

        when(loginUser.getRoleId()).thenReturn(1);

        when(repository.findAll())
                .thenReturn(java.util.Arrays.asList(dto));

       List<UserListDto> result = userListService.findAll(loginUser);
        assertEquals(1, result.size());

        verify(repository).findAll();

    }

    @Test
    void 指導員は自部署のユーザー一覧を取得できる() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        when(repository.findByDepartmentId(1))
                .thenReturn(java.util.Arrays.asList(dto));

        List<UserListDto> result = userListService.findAll(loginUser);
        assertEquals(1, result.size());

        verify(repository).findByDepartmentId(1);  
    }

    //異常パターン

    @Test 
    void リリーフはユーザー一覧を取得できない() {

        when(loginUser.getRoleId()).thenReturn(3);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
             () -> {userListService.findAll(loginUser);}
        );

        assertEquals("ユーザー一覧を参照する権限がありません", exception.getMessage());

        verify(repository, never()).findAll();
        verify(repository, never()).findByDepartmentId(any(Integer.class));
    }

    @Test
    void 作業者はユーザー一覧を取得できない() {

        when(loginUser.getRoleId()).thenReturn(4);

        ForbiddenOperationException exception = assertThrows(
            ForbiddenOperationException.class,
             () -> {userListService.findAll(loginUser);}
        );

        assertEquals("ユーザー一覧を参照する権限がありません", exception.getMessage());

        verify(repository, never()).findAll();
        verify(repository, never()).findByDepartmentId(any(Integer.class));
    }
}
