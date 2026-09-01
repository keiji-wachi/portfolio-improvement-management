package com.example.demo.Repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.LoginUserDto;

@Repository
public class LoginRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

public LoginUserDto findByEmployeeNo(String employeeNo) {

    String sql = """
        SELECT
            id,
            employee_number,
            password,
            department_id,
            role_id,
            first_login_flag
        FROM user_mst
        WHERE employee_number = ?
        AND is_deleted = false
        """;

    try {
        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new LoginUserDto(
                rs.getInt("id"),
                rs.getString("employee_number"),
                rs.getString("password"),
                rs.getInt("department_id"),
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
