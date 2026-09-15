package com.example.improvementmanagement.user.repository;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.user.dto.UserDetailDto;

@Repository
public class UserGetRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserGetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserDetailDto> findById(Integer id) {

        String sql = """
            SELECT
                id,
                employee_number,
                name,
                department_id,
                role_id
            FROM user_mst
            WHERE id = ?
              AND is_deleted = false
            """;

        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new UserDetailDto(
                rs.getInt("id"),
                rs.getString("employee_number"),
                rs.getString("name"),
                rs.getInt("department_id"),
                rs.getInt("role_id")
            ),
            id
        ).stream().findFirst();
    }
}