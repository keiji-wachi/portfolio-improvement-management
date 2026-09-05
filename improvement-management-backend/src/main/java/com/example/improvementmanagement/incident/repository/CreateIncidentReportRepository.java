package com.example.improvementmanagement.incident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.incident.dto.CreateIncidentReportDto;

@Repository
public class CreateIncidentReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public CreateIncidentReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByProcessId(int processId) {
        String sql = "SELECT COUNT(*) FROM process_mst WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,processId);
        return count != null && count > 0;
    }

    public boolean existsByIncidentTypeId(int incidentTypeId) {
        String sql = "SELECT COUNT(*) FROM incident_type_mst WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql,Integer.class,incidentTypeId);
        return count != null && count > 0;
    }

    public int createIncidentReport(CreateIncidentReportDto dto, int departmentId, int reportUserId) {
        String sql = "INSERT INTO incident_response (department_id, report_user_id, occurred_process_id, incident_type_id, incident_detail, action_taken) VALUES(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, departmentId, reportUserId, dto.getOccurredProcessId(), dto.getIncidentTypeId(), dto.getIncidentDetail(), dto.getActionTaken());
    }
}
