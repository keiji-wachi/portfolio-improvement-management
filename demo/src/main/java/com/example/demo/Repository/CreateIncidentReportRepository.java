package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.CreateIncidentReportDto;

@Repository
public class CreateIncidentReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public CreateIncidentReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createIncidentReport(CreateIncidentReportDto dto){
        String sql = "INSERT INTO incident_response (department_id, report_user_id, occurred_process_id, incident_type_id, incident_detail, action_taken) VALUES(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, dto.getDepartmentId(), dto.getReportUserId(), dto.getOccurredProcessId(), dto.getIncidentTypeId(), dto.getIncidentDetail(), dto.getActionTaken());
    }
}
