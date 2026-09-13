package com.example.improvementmanagement.integration.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.improvementmanagement.auth.security.CustomUserDetails;

import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

@Test
@Sql("/integration/testdata/user/create-success.sql")
void システム管理者はユーザー作成可能() throws Exception {

    CustomUserDetails admin = new CustomUserDetails(
        1,
        "admin0001",
        1,
        1,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(admin))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 1,
                "role_id": 2,
                "password": "test1234"
            }
            """))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    Integer count = jdbcTemplate.queryForObject(
        """
        SELECT COUNT(*)
        FROM user_mst
        WHERE employee_number = ?
        """,
        Integer.class,
        "test0001"
    );

    assertEquals(1, count);
    }

@Test
@Sql("/integration/testdata/user/create-success.sql")
void 指導員は自部署のリリーフユーザー作成可能() throws Exception {

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(instructor))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 1,
                "role_id": 3,
                "password": "test1234"
            }
            """))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    Integer count = jdbcTemplate.queryForObject(
        """
        SELECT COUNT(*)
        FROM user_mst
        WHERE employee_number = ?
        """,
        Integer.class,
        "test0001"
    );

    assertEquals(1, count);
    }

    @Test
@Sql("/integration/testdata/user/create-success.sql")
void 指導員は自部署の作業者ユーザー作成可能() throws Exception {

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(instructor))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 1,
                "role_id": 4,
                "password": "test1234"
            }
            """))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    Integer count = jdbcTemplate.queryForObject(
        """
        SELECT COUNT(*)
        FROM user_mst
        WHERE employee_number = ?
        """,
        Integer.class,
        "test0001"
    );

    assertEquals(1, count);
    }

@Test
@Sql("/integration/testdata/user/create-success.sql")
void 指導員は他部署ユーザー作成できない() throws Exception {

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(instructor))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 2,
                "role_id": 3,
                "password": "test1234"
            }
            """))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }

    @Test
    @Sql("/integration/testdata/user/create-success.sql")
    void 指導員はシステム管理者ユーザー作成できない() throws Exception {

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(instructor))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 1,
                "role_id": 1,
                "password": "test1234"
            }
            """))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }

    @Test
    @Sql("/integration/testdata/user/create-success.sql")
    void 指導員は指導員ユーザー作成できない() throws Exception {

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(instructor))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 1,
                "role_id": 2,
                "password": "test1234"
            }
            """))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }

    @Test
    @Sql("/integration/testdata/user/user-duplicate.sql")
    void 重複した社員番号のユーザーを作成できない() throws Exception {

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "Adomin0001",
        1,
        1,
        "dummy",
        false
    );

    mockMvc.perform(post("/users")
            .with(user(instructor))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
                "employeeNo": "test0001",
                "name": "テストユーザー",
                "department_id": 1,
                "role_id": 3,
                "password": "test1234"
            }
            """))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").value("CONFLICT"))
            .andExpect(jsonPath("$.message").value("この社員番号はすでに登録されています"));
    }
}




