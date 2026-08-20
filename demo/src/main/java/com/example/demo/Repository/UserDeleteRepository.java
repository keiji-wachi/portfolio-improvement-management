package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDeleteRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserDeleteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int deleteUser(int id){
        String sql = "DELETE FROM user_mst WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
