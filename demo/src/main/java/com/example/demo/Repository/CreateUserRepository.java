package com.example.demo.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Dto.CreateUserDto;

@Repository
public class CreateUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CreateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public int createUser(CreateUserDto dto){
        String sql = "INSERT INTO user_mst (name, department_id, role_id, first_login_flag, password) VALUES(?, ?, ?, true, ?)";
        return jdbcTemplate.update(sql, 
            dto.getName(),
            dto.getDepartment_id(),
            dto.getRole_id(),
            dto.getPassword());
    }
}
