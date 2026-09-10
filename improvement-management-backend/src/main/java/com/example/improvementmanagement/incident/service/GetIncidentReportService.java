package com.example.improvementmanagement.incident.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.incident.dto.IncidentReportResponseDto;
import com.example.improvementmanagement.incident.repository.GetIncidentReportRepository;

@Service
public class GetIncidentReportService {

    private final GetIncidentReportRepository repository;
        
    public GetIncidentReportService(GetIncidentReportRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("@incidentAuthorization.canGet(authentication)")
    public List<IncidentReportResponseDto> searchByMonth(YearMonth targetMonth,CustomUserDetails loginUser) {
        YearMonth searchMonth;

        if (targetMonth == null) {
            searchMonth = YearMonth.now();
        } else {
            searchMonth = targetMonth;
        }

        LocalDateTime from = searchMonth.atDay(1).atStartOfDay();
        LocalDateTime to = searchMonth.plusMonths(1).atDay(1).atStartOfDay();
        int departmentId = loginUser.getDepartmentId();

        return repository.findByMonth(from, to, departmentId);
    }
}
