package com.example.improvementmanagement.auth.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.auth.dto.LoginUserDto;

@Repository
public class LoginRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

public LoginUserDto findByEmployeeNo(String employeeNo) {

    String sql = """
        SELECT
            u.id,
            u.employee_number,
            u.name,
            u.password,
            u.department_id,
            d.department_name,
            u.role_id,
            u.first_login_flag
        FROM user_mst u
        INNER JOIN department_mst d
            ON u.department_id = d.department_id
        WHERE u.employee_number = ?
          AND u.is_deleted = false
        """;

    try {
        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new LoginUserDto(
                rs.getInt("id"),
                rs.getString("employee_number"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getInt("department_id"),
                rs.getString("department_name"),
                rs.getInt("role_id"),
                rs.getBoolean("first_login_flag")
            ),
            employeeNo
        );

    } catch (EmptyResultDataAccessException e) {
        return null;
    }
}
}
