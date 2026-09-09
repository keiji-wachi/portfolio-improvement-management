package com.example.improvementmanagement.incident.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

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
import com.example.improvementmanagement.incident.dto.IncidentReportResponseDto;
import com.example.improvementmanagement.incident.repository.GetIncidentReportRepository;
import com.example.improvementmanagement.incident.service.GetIncidentReportService;


@ExtendWith(MockitoExtension.class)
public class GetIncidentReportServiceTest {

    @Mock 
    private GetIncidentReportRepository repository;

    @Mock
    private IncidentReportResponseDto dto;

    @Mock 
    private CustomUserDetails loginUser;

    @InjectMocks 
    private GetIncidentReportService getIncidentReportService;

    //正常パターン

    @Test 
    void 指導員は異常対応記録の閲覧ができる() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);

        YearMonth targetMonth = YearMonth.of(2026, 9);

        LocalDateTime from = LocalDateTime.of(2026, 9, 1, 0, 0);

        LocalDateTime to = LocalDateTime.of(2026, 10, 1, 0, 0);

        List<IncidentReportResponseDto> expected = List.of(dto);

        when(repository.findByMonth(from, to, 1))
            .thenReturn(expected);

        List<IncidentReportResponseDto> result =
            getIncidentReportService.searchByMonth(
                targetMonth,
                loginUser
            );

        assertEquals(expected, result);

        verify(repository)
            .findByMonth(from, to, 1);
    }

    @Test
    void リリーフは異常対応記録の閲覧ができる() {

        when(loginUser.getRoleId()).thenReturn(3);
        when(loginUser.getDepartmentId()).thenReturn(1);

        YearMonth targetMonth = YearMonth.of(2026, 9);

        LocalDateTime from = LocalDateTime.of(2026, 9, 1, 0, 0);

        LocalDateTime to = LocalDateTime.of(2026, 10, 1, 0, 0);

        List<IncidentReportResponseDto> expected = List.of(dto);

        when(repository.findByMonth(from, to, 1))
            .thenReturn(expected);

        List<IncidentReportResponseDto> result =
            getIncidentReportService.searchByMonth(
                targetMonth,
                loginUser
            );

        assertEquals(expected, result);

        verify(repository)
            .findByMonth(from, to, 1);
    }

    @Test 
    void 作業者は異常対応記録の閲覧ができる() {

        when(loginUser.getRoleId()).thenReturn(4);
        when(loginUser.getDepartmentId()).thenReturn(1);

        YearMonth targetMonth = YearMonth.of(2026, 9);

        LocalDateTime from = LocalDateTime.of(2026, 9, 1, 0, 0);

        LocalDateTime to = LocalDateTime.of(2026, 10, 1, 0, 0);

        List<IncidentReportResponseDto> expected = List.of(dto);

        when(repository.findByMonth(from, to, 1))
            .thenReturn(expected);

        List<IncidentReportResponseDto> result =
            getIncidentReportService.searchByMonth(
                targetMonth,
                loginUser
            );

        assertEquals(expected, result);

        verify(repository)
            .findByMonth(from, to, 1);
    }

    @Test
    void 月指定がnullの場合は現在の年月で検索する() {

        when(loginUser.getRoleId()).thenReturn(2);
        when(loginUser.getDepartmentId()).thenReturn(1);
        YearMonth currentMonth = YearMonth.now();

        LocalDateTime from = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime to = currentMonth.plusMonths(1).atDay(1).atStartOfDay();

        List<IncidentReportResponseDto> expected = List.of(dto);

        when(repository.findByMonth(from, to, 1))
            .thenReturn(expected);

        List<IncidentReportResponseDto> result =
            getIncidentReportService.searchByMonth(
                null,
                loginUser
            );

        assertEquals(expected, result);

        verify(repository)
            .findByMonth(from, to, 1);
    }

    //異常パターン[
    
    @Test 
    void 指導員は異常対応記録の閲覧ができない() {

        when(loginUser.getRoleId()).thenReturn(1);

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> {
            getIncidentReportService.searchByMonth(null, loginUser);
        });

        assertEquals("異常対応記録の閲覧権限がありません", exception.getMessage());

        verify(repository, never()).findByMonth(any(LocalDateTime.class), any(LocalDateTime.class), anyInt());
    }
}
