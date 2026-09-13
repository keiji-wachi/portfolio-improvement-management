package com.example.improvementmanagement.integration.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
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
public class UserDeleteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

@Test
@Sql("/integration/testdata/user/delete-success.sql")
void システム管理者はユーザーを論理削除可能() throws Exception {

    CustomUserDetails admin = new CustomUserDetails(
        1,
        "admin0001",
        1,
        1,
        "dummy",
        false
    );

    mockMvc.perform(delete("/users/{id}",2)
            .with(user(admin)))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    Integer count = jdbcTemplate.queryForObject(
        """
        SELECT COUNT(*)
        FROM user_mst
        WHERE id = ?
        AND is_deleted = TRUE
        """,
        Integer.class,
        2
    );

    assertEquals(1, count);
    }

@Test
@Sql("/integration/testdata/user/delete-success.sql")
void 指導員は自部署のリリーフユーザーを論理削除可能() throws Exception{

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(delete("/users/{id}",3)
            .with(user(instructor)))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    Integer count = jdbcTemplate.queryForObject(
        """
        SELECT COUNT(*)
        FROM user_mst
        WHERE id = ?
        AND is_deleted = TRUE
        """,
        Integer.class,
        3
    );

    assertEquals(1, count);
    }

@Test
@Sql("/integration/testdata/user/delete-success.sql")
void 指導員は自部署の作業者ユーザーを論理削除可能() throws Exception{

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(delete("/users/{id}",4)
            .with(user(instructor)))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));

    Integer count = jdbcTemplate.queryForObject(
        """
        SELECT COUNT(*)
        FROM user_mst
        WHERE id = ?
        AND is_deleted = TRUE
        """,
        Integer.class,
        4
    );

    assertEquals(1, count);
    }

@Test
@Sql("/integration/testdata/user/delete-other-dep.sql")
void 指導員は他部署のユーザーを論理削除できない() throws Exception{

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );

    mockMvc.perform(delete("/users/{id}",1)
            .with(user(instructor)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }

@Test
@Sql("/integration/testdata/user/delete-myself.sql")
void 自分自身を論理削除できない() throws Exception{

    CustomUserDetails admin = new CustomUserDetails(
        1,
        "admin0001",
        1,
        1,
        "dummy",
        false
    );

    mockMvc.perform(delete("/users/{id}",1)
            .with(user(admin)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }

@Test
@Sql("/integration/testdata/user/delete-not-allowed-role.sql")
void 指導員はシステム管理者を論理削除できない() throws Exception{

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );


    mockMvc.perform(delete("/users/{id}",2)
            .with(user(instructor)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }

@Test
@Sql("/integration/testdata/user/delete-not-allowed-role.sql")
void 指導員は指導員を論理削除できない() throws Exception{

    CustomUserDetails instructor = new CustomUserDetails(
        1,
        "instructor0001",
        1,
        2,
        "dummy",
        false
    );


    mockMvc.perform(delete("/users/{id}",3)
            .with(user(instructor)))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error").value("Forbidden"))
            .andExpect(jsonPath("$.message").value("この操作を実行する権限がありません"));
    }
    
@Test
void 存在しないユーザーを論理削除できない() throws Exception{

    CustomUserDetails admin = new CustomUserDetails(
        1,
        "admin0001",
        1,
        1,
        "dummy",
        false
    );

    mockMvc.perform(delete("/users/{id}",1)
            .with(user(admin)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("削除対象ユーザーが存在しません"));
    }
}
