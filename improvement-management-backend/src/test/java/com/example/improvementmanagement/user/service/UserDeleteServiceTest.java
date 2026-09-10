package com.example.improvementmanagement.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.common.exception.ResourceNotFoundException;
import com.example.improvementmanagement.user.dto.UserDeleteTargetDto;
import com.example.improvementmanagement.user.repository.UserDeleteRepository;

@ExtendWith(MockitoExtension.class)
class UserDeleteServiceTest {

    @InjectMocks
    private UserDeleteService userDeleteService;

    @Mock
    private UserDeleteTargetDto dto;

    @Mock
    private UserDeleteRepository repository;

    @Test
    void 正常にユーザーを削除できる() {

        when(repository.findById(1))
                .thenReturn(dto);

        when(repository.logicalDelete(1))
                .thenReturn(1);

        int result = userDeleteService.deleteUser(1);

        assertEquals(1, result);

        verify(repository).logicalDelete(1);
    }

    @Test
    void 存在しないユーザーを削除できない() {

        when(repository.findById(11))
                .thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userDeleteService.deleteUser(11)
        );

        assertEquals(
                "削除対象ユーザーが存在しません",
                exception.getMessage()
        );

        verify(repository, never())
                .logicalDelete(any());
    }

    @Test
    void 不正なユーザーIDで削除できない() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userDeleteService.deleteUser(-1)
        );

        assertEquals(
                "削除対象ユーザーIDが不正です",
                exception.getMessage()
        );

        verify(repository, never())
                .logicalDelete(any());
    }
}