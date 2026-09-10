package com.example.improvementmanagement.incident.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.incident.dto.CreateIncidentReportDto;
import com.example.improvementmanagement.incident.repository.CreateIncidentReportRepository;
import com.example.improvementmanagement.common.exception.ResourceNotFoundException;

@Service
public class CreateIncidentReportService {
    private  final CreateIncidentReportRepository repository;

    public CreateIncidentReportService(CreateIncidentReportRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("@incidentAuthorization.canCreate(authentication)")
    public int createIncidentReport(CreateIncidentReportDto dto, CustomUserDetails loginUser){

        int departmentId = loginUser.getDepartmentId();
        int reportUserId = loginUser.getUserId();
        Integer occurredProcessId = dto.getOccurredProcessId();
        Integer incidentTypeId = dto.getIncidentTypeId();

        if (!repository.existsByProcessId(occurredProcessId)) {
            throw new ResourceNotFoundException("指定された工程は存在しません");
        }

        if (!repository.existsByIncidentTypeId(incidentTypeId)) {
            throw new ResourceNotFoundException("指定された異常種別は存在しません");
        }

        int result = repository.createIncidentReport(dto, departmentId, reportUserId);
        if (result != 1) {
            throw new RuntimeException("異常対応入力に失敗しました");
        }
        return result;
    }
}
