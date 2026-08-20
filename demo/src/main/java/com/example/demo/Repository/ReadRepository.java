package com.example.demo.Repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.CRUDDto;

@Repository
public class ReadRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public List<CRUDDto> findAll(){
        String sql = "SELECT id, name FROM test_items";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new CRUDDto(rs.getInt("id"), rs.getString("name"))
            );
    }
}
