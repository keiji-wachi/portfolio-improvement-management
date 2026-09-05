package com.example.improvementmanagement.incident.service;

import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.incident.dto.CreateIncidentReportDto;
import com.example.improvementmanagement.incident.repository.CreateIncidentReportRepository;

@Service
public class CreateIncidentReportService {
    private  final CreateIncidentReportRepository repository;
    private static final int INSTRUCTOR = 2;
    private static final int RELIEF = 3;

    public CreateIncidentReportService(CreateIncidentReportRepository repository){
        this.repository = repository;
    }

    public int createIncidentReport(CreateIncidentReportDto dto, CustomUserDetails loginUser){

        int loginRoleId = loginUser.getRoleId();
        if (loginRoleId != INSTRUCTOR && loginRoleId != RELIEF) {
            throw new IllegalStateException("異常対応入力権限がありません");
        }

        int departmentId = loginUser.getDepartmentId();
        int reportUserId = loginUser.getUserId();
        Integer occurredProcessId = dto.getOccurredProcessId();
        Integer incidentTypeId = dto.getIncidentTypeId();

        if (!repository.existsByProcessId(occurredProcessId)) {
            throw new IllegalArgumentException("指定された工程は存在しません");
        }

        if (!repository.existsByIncidentTypeId(incidentTypeId)) {
            throw new IllegalArgumentException("指定された異常種別は存在しません");
        }

        int result = repository.createIncidentReport(dto, departmentId, reportUserId);
        if (result != 1) {
            throw new IllegalStateException("異常対応入力に失敗しました");
        }
        return result;
    }
}
