package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateRepository {
    private final JdbcTemplate jdbcTemplate;

    public UpdateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int update(int id, String name){
    String sql = "UPDATE test_items SET name = ? WHERE id = ?";
    return jdbcTemplate.update(sql,  name,id);
    }

}
