package com.example.improvementmanagement.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.dto.UserUpdateTargetDto;

@Repository
public class UserUpdateRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserUpdateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserUpdateTargetDto> findById(Integer id) {

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

        List<UserUpdateTargetDto> users = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new UserUpdateTargetDto(
                        rs.getInt("id"),
                        rs.getString("employee_number"),
                        rs.getString("name"),
                        rs.getInt("department_id"),
                        rs.getInt("role_id")
                ),
                id
        );

        return users.stream().findFirst();
    }

    public int updateUser(Integer id, UserUpdateDto dto) {

        String sql = """
                UPDATE user_mst
                SET
                    name = ?,
                    department_id = ?,
                    role_id = ?
                WHERE id = ?
                AND is_deleted = false
                """;

        return jdbcTemplate.update(
                sql,
                dto.getName(),
                dto.getDepartmentId(),
                dto.getRoleId(),
                id
        );
    }
}