package com.example.improvementmanagement.master.repository;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.master.dto.DepartmentDto;
import com.example.improvementmanagement.master.dto.IncidentTypeDto;
import com.example.improvementmanagement.master.dto.ProcessDto;
import com.example.improvementmanagement.master.dto.RoleDto;

@Repository
public class MstSearchRepository {
    private final JdbcTemplate jdbcTemplate;

    public MstSearchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DepartmentDto> DepartmentFindAll(){
        String sql ="SELECT department_id, department_name FROM department_mst";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DepartmentDto(rs.getInt("department_id"), rs.getString("department_name")));
    }

    public List<RoleDto> RoleFindAll(){
        String sql ="SELECT role_id, role_name FROM role_mst";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RoleDto(rs.getInt("role_id"), rs.getString("role_name")));
    }

    public List<ProcessDto> ProcessFindByDepartmentId(int departmentId) {
        String sql = """
            SELECT id, process_name
            FROM process_mst
            WHERE department_id = ?
              AND active_flag = 1
            ORDER BY id
            """;

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new ProcessDto(
                rs.getInt("id"),
                rs.getString("process_name")
            ),
            departmentId
        );
    }

    public List<IncidentTypeDto> IncidentTypeFindByDepartmentId(
            int departmentId) {

        String sql = """
            SELECT id, incident_type_name
            FROM incident_type_mst
            WHERE department_id = ?
              AND active_flag = 1
            ORDER BY id
            """;

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new IncidentTypeDto(
                rs.getInt("id"),
                rs.getString("incident_type_name")
            ),
            departmentId
        );
    }
}
