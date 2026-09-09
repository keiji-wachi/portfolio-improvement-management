package com.example.improvementmanagement.incident.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.common.exception.ForbiddenOperationException;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;
import com.example.improvementmanagement.incident.dto.CreateIncidentReportDto;
import com.example.improvementmanagement.incident.repository.CreateIncidentReportRepository;
import com.example.improvementmanagement.incident.service.CreateIncidentReportService;

@ExtendWith(MockitoExtension.class)
public class CreateIncidentReportServiceTest {

    @Mock 
    private CreateIncidentReportRepository repository;

    @Mock
    private CreateIncidentReportDto dto;

    @Mock 
    private CustomUserDetails loginUser;

    @InjectMocks 
    private CreateIncidentReportService createIncidentReportService;

    //正常パターン

    @Test 
    void 指導員は異常対応入力ができる() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);
        when(loginUser.getUserId()).thenReturn(1);


        when(dto.getOccurredProcessId()).thenReturn(1);
        when(dto.getIncidentTypeId()).thenReturn(1);

        when(repository.existsByProcessId(1)).thenReturn(true);
        when(repository.existsByIncidentTypeId(1)).thenReturn(true);
        when(repository.createIncidentReport(dto, 1, 1)).thenReturn(1);

        int result = createIncidentReportService.createIncidentReport(dto, loginUser);

        assertEquals(1, result);

        verify(repository).createIncidentReport(dto, 1, 1);
    }

    @Test
    void リリーフは異常対応入力ができる() {

        when(loginUser.getRoleId()).thenReturn(3);
        when(loginUser.getDepartmentId()).thenReturn(1);
        when(loginUser.getUserId()).thenReturn(1);

        when(dto.getOccurredProcessId()).thenReturn(1);
        when(dto.getIncidentTypeId()).thenReturn(1);

        when(repository.existsByProcessId(1)).thenReturn(true);
        when(repository.existsByIncidentTypeId(1)).thenReturn(true);
        when(repository.createIncidentReport(dto, 1, 1)).thenReturn(1); 

        int result = createIncidentReportService.createIncidentReport(dto, loginUser);

        assertEquals(1, result);

        verify(repository).createIncidentReport(dto, 1, 1);
    }

    //異常パターン

    @Test 
    void システム管理者は異常対応入力ができない() {

        when(loginUser.getRoleId()).thenReturn(1);

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            createIncidentReportService.createIncidentReport(dto, loginUser);
        });

        assertEquals("異常対応入力権限がありません", exception.getMessage());

        verify(repository, never()).createIncidentReport(any(CreateIncidentReportDto.class), anyInt(), anyInt());
    }

    @Test 
    void 作業者は異常対応入力ができない() {

        when(loginUser.getRoleId()).thenReturn(4);

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            createIncidentReportService.createIncidentReport(dto, loginUser);
        });

        assertEquals("異常対応入力権限がありません", exception.getMessage());

        verify(repository, never()).createIncidentReport(any(CreateIncidentReportDto.class), anyInt(), anyInt());
    }

    @Test 
    void 存在しない工程の異常対応入力はできない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(dto.getOccurredProcessId()).thenReturn(1);
        when(repository.existsByProcessId(1)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            createIncidentReportService.createIncidentReport(dto, loginUser);
        });

        assertEquals("指定された工程は存在しません", exception.getMessage());

        verify(repository, never()).createIncidentReport(any(CreateIncidentReportDto.class), anyInt(), anyInt());
    }

    @Test 
    void 存在しない異常種別の異常対応入力はできない() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(dto.getOccurredProcessId()).thenReturn(1);
        when(dto.getIncidentTypeId()).thenReturn(1);
        when(repository.existsByProcessId(1)).thenReturn(true);
        when(repository.existsByIncidentTypeId(1)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            createIncidentReportService.createIncidentReport(dto, loginUser);
        });

        assertEquals("指定された異常種別は存在しません", exception.getMessage());

        verify(repository, never()).createIncidentReport(any(CreateIncidentReportDto.class), anyInt(), anyInt());
    }
}
