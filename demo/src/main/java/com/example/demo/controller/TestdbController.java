package com.example.demo.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestdbController {

    private final JdbcTemplate jdbcTemplate;

    public TestdbController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/test")
    public String test() {
        return jdbcTemplate.queryForObject(
            "SELECT name FROM test_items LIMIT 1",
            String.class
        );
    }
}