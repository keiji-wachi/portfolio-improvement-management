package com.example.improvementmanagement.user.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.improvementmanagement.user.dto.CreateUserDto;

@Repository
public class CreateUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public CreateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        // ユーザー番号の重複チェック
        public boolean existsByEmployeeNo(String employeeNo) {
            String sql = "SELECT COUNT(*) FROM user_mst WHERE employee_number = ?";

            Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                employeeNo
            );

            return count != null && count > 0;
        }

        // 部署IDの存在チェック
        public boolean existsDepartmentById(Integer departmentId) {
            String sql = "SELECT COUNT(*) FROM department_mst WHERE department_id = ?";

            Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                departmentId
            );

            return count != null && count > 0;
        }

        // 役職IDの存在チェック
        public boolean existsRoleById(Integer roleId) {
            String sql = "SELECT COUNT(*) FROM role_mst WHERE role_id = ?";

            Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                roleId
            );
            return count != null && count > 0;
        }

        // ユーザー作成
        public int createUser(CreateUserDto dto, String passwordHash) {
    String sql = """
        INSERT INTO user_mst
        (
            name,
            department_id,
            role_id,
            password,
            first_login_flag,
            employee_number
        )
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    return jdbcTemplate.update(
        sql,
        dto.getName(),
        dto.getDepartment_id(),
        dto.getRole_id(),
        passwordHash,
        true,
        dto.getEmployeeNo()
    );
    }
}
