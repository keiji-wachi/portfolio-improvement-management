package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.UserDeleteTargetDto;

@Repository
public class UserDeleteRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserDeleteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDeleteTargetDto findById(Integer id) {
        String sql = "SELECT id, department_id, role_id FROM user_mst WHERE id = ? AND is_deleted = false";
        return jdbcTemplate.query(sql,(rs, rowNum) -> new UserDeleteTargetDto(rs.getInt("id"),rs.getInt("department_id"),rs.getInt("role_id")),id).stream().findFirst().orElse(null);
    }

    public int logicalDelete(Integer id) {

        String sql = "UPDATE user_mst SET is_deleted = true WHERE id = ? AND is_deleted = false";
        return jdbcTemplate.update(sql, id);
    }
}
