package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Dto.UserUpdateTargetDto;

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