package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.UserListDto;
import com.example.improvementmanagement.user.repository.UserListRepository;

@ExtendWith(MockitoExtension.class)
class UserListServiceTest {

    @InjectMocks
    private UserListService userListService;

    @Mock
    private CustomUserDetails loginUser;

    @Mock
    private UserListDto dto;

    @Mock
    private UserListRepository repository;

    @Test
    void システム管理者はユーザー一覧を取得できる() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        when(repository.findAll())
                .thenReturn(List.of(dto));

        List<UserListDto> result =
                userListService.findAll(loginUser);

        assertEquals(1, result.size());

        verify(repository).findAll();
    }

    @Test
    void 指導員は自部署のユーザー一覧を取得できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        when(loginUser.getDepartmentId())
                .thenReturn(1);

        when(repository.findByDepartmentId(1))
                .thenReturn(List.of(dto));

        List<UserListDto> result =
                userListService.findAll(loginUser);

        assertEquals(1, result.size());

        verify(repository)
                .findByDepartmentId(1);
    }
}