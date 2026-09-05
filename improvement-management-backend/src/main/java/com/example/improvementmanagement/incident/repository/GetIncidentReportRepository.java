package com.example.improvementmanagement.incident.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.incident.dto.IncidentReportResponseDto;

@Repository
public class GetIncidentReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public GetIncidentReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public List<IncidentReportResponseDto> findByMonth(LocalDateTime from, LocalDateTime to, int departmentId) {
        String sql = "SELECT ir.incident_id, d.department_name AS department_name, u.name AS report_user_name, ir.reported_at, p.process_name AS process_name, it.incident_type_name AS incident_type_name, ir.incident_detail, ir.action_taken FROM incident_response ir JOIN department_mst d ON ir.department_id = d.department_id JOIN user_mst u ON ir.report_user_id = u.id JOIN process_mst p ON ir.occurred_process_id = p.id JOIN incident_type_mst it ON ir.incident_type_id = it.id WHERE ir.reported_at >= ? AND ir.reported_at < ? AND ir.department_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new IncidentReportResponseDto(rs.getInt("incident_id"), rs.getString("department_name"), 
            rs.getString("report_user_name"), rs.getTimestamp("reported_at").toLocalDateTime(), rs.getString("process_name"), rs.getString("incident_type_name"), rs.getString("incident_detail"), rs.getString("action_taken")), from,to,departmentId);
        }   


}
