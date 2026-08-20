package com.example.demo.Repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.Dto.UserListDto;

@Repository
public class UserListRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserListDto> findAll(){
        String sql = "SELECT u.id, u.name, u.department_id, u.role_id, d.department_name AS department_name, r.role_name AS role_name FROM user_mst u JOIN department_mst d ON u.department_id = d.department_id JOIN role_mst r ON u.role_id = r.role_id;";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new UserListDto(rs.getInt("id"), rs.getString("name"), 
            rs.getInt("department_id"), rs.getInt("role_id"), rs.getString("department_name"), rs.getString("role_name"))
        );
    }
}
