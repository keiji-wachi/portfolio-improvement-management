package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeleteRepository {
    private final JdbcTemplate jdbcTemplate;

    public DeleteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int delete(int id){
        String sql = "DELETE FROM test_items WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
