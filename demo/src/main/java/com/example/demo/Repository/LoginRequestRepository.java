package com.example.demo.Repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;

@Repository
public class LoginRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginRequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LoginResponseDto loginAuth(LoginRequestDto dto){
        String sql = "SELECT id, department_id, role_id FROM user_mst WHERE id = ? AND password = ?";

        try{
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new LoginResponseDto(rs.getInt("id"), rs.getInt("department_id"), rs.getInt("role_id"), true), dto.getId(), dto.getPassWord());
        }catch(EmptyResultDataAccessException e){
            return new LoginResponseDto(0, 0, 0, false);
        }
    }
}
