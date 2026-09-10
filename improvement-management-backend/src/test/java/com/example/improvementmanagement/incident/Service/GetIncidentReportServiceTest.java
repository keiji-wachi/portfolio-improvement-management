package com.example.improvementmanagement.incident.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.incident.dto.IncidentReportResponseDto;
import com.example.improvementmanagement.incident.repository.GetIncidentReportRepository;

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

    @Test 
    void 指定月で自部署の異常対応記録を取得できる() {

        when(loginUser.getDepartmentId()).thenReturn(1);

        YearMonth targetMonth = YearMonth.of(2026, 9);

        LocalDateTime from =
                LocalDateTime.of(2026, 9, 1, 0, 0);

        LocalDateTime to =
                LocalDateTime.of(2026, 10, 1, 0, 0);

        List<IncidentReportResponseDto> expected =
                List.of(dto);

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

        when(loginUser.getDepartmentId()).thenReturn(1);

        YearMonth currentMonth = YearMonth.now();

        LocalDateTime from =
                currentMonth.atDay(1).atStartOfDay();

        LocalDateTime to =
                currentMonth.plusMonths(1)
                        .atDay(1)
                        .atStartOfDay();

        List<IncidentReportResponseDto> expected =
                List.of(dto);

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
}