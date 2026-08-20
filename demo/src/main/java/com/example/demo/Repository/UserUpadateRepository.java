package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.UserUpdateDto;

@Repository
public class UserUpadateRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserUpadateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int userUpdate(int id, UserUpdateDto dto){
            String sql = "UPDATE user_mst SET name = ?, department_id=?, role_id=? WHERE id = ?";
            return jdbcTemplate.update(sql,  dto.getName(), dto.getDepartmentId(), dto.getRoleId(), id);
    }
}
