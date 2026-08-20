package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateRepository {
    private final JdbcTemplate jdbcTemplate;

    public CreateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(String name){
        String sql = "INSERT INTO test_items (name) VALUES(?)";
        return jdbcTemplate.update(sql, name);
    }


}
