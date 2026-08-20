package com.example.demo.Repository;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.RoleDto;

@Repository
public class MstSearchRepository {
    private final JdbcTemplate jdbcTemplate;

    public MstSearchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DepartmentDto> DepartmentFindAll(){
        String sql ="SELECT department_id, department_name FROM department_mst";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DepartmentDto(rs.getInt("department_id"), rs.getString("department_name")));
    }

    public List<RoleDto> RoleFindAll(){
        String sql ="SELECT role_id, role_name FROM role_mst";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RoleDto(rs.getInt("role_id"), rs.getString("role_name")));
    }
}
