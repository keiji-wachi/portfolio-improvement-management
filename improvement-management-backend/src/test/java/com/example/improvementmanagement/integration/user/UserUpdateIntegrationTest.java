package com.example.improvementmanagement.integration.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.improvementmanagement.auth.security.CustomUserDetails;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserUpdateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /*
     * =========================
     * テスト用ログインユーザー
     * =========================
     */

    private CustomUserDetails createAdmin() {

        return new CustomUserDetails(
            1,
            "admin0001",
            "システム管理者",
            1,
            "プレス部署",
            1,
            "dummy",
            false
        );
    }


    private CustomUserDetails createInstructor() {

        return new CustomUserDetails(
            1,
            "instructor0001",
            "プレス部署指導員",
            1,
            "プレス部署",
            2,
            "dummy",
            false
        );
    }


    /*
     * =========================
     * システム管理者
     * 更新成功
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/update-success.sql")
    void システム管理者はユーザーを更新可能()
            throws Exception {

        CustomUserDetails admin =
            createAdmin();

        mockMvc.perform(
                put("/users/{id}", 2)
                    .with(user(admin))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テスト更新後ユーザー",
                        "departmentId": 2,
                        "roleId": 2
                    }
                    """)
            )
            .andExpect(
                status().isOk()
            );

        Integer count =
            jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM user_mst
                WHERE id = ?
                AND name = ?
                AND department_id = ?
                AND role_id = ?
                """,
                Integer.class,
                2,
                "テスト更新後ユーザー",
                2,
                2
            );

        assertEquals(
            1,
            count
        );
    }


    /*
     * =========================
     * 指導員
     * 自部署リリーフ更新成功
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/update-success.sql")
    void 指導員は自部署のリリーフユーザーを更新可能()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                put("/users/{id}", 3)
                    .with(user(instructor))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テスト更新後リリーフユーザー",
                        "departmentId": 1,
                        "roleId": 4
                    }
                    """)
            )
            .andExpect(
                status().isOk()
            );

        Integer count =
            jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM user_mst
                WHERE id = ?
                AND name = ?
                AND department_id = ?
                AND role_id = ?
                """,
                Integer.class,
                3,
                "テスト更新後リリーフユーザー",
                1,
                4
            );

        assertEquals(
            1,
            count
        );
    }


    /*
     * =========================
     * 指導員
     * 自部署作業者更新成功
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/update-success.sql")
    void 指導員は自部署の作業者ユーザーを更新可能()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                put("/users/{id}", 4)
                    .with(user(instructor))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テスト更新後作業者ユーザー",
                        "departmentId": 1,
                        "roleId": 3
                    }
                    """)
            )
            .andExpect(
                status().isOk()
            );

        Integer count =
            jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM user_mst
                WHERE id = ?
                AND name = ?
                AND department_id = ?
                AND role_id = ?
                """,
                Integer.class,
                4,
                "テスト更新後作業者ユーザー",
                1,
                3
            );

        assertEquals(
            1,
            count
        );
    }


    /*
     * =========================
     * 指導員
     * 他部署ユーザー更新不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/update-other-dep.sql")
    void 指導員は他部署のユーザーを更新できない()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                put("/users/{id}", 5)
                    .with(user(instructor))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テストユーザー",
                        "departmentId": 1,
                        "roleId": 3
                    }
                    """)
            )
            .andExpect(
                status().isForbidden()
            )
            .andExpect(
                jsonPath("$.error")
                    .value("Forbidden")
            )
            .andExpect(
                jsonPath("$.message")
                    .value(
                        "この操作を実行する権限がありません"
                    )
            );
    }


    /*
     * =========================
     * 指導員
     * SYSTEM_ADMINへ更新不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/update-not-allowed-role.sql")
    void 指導員はユーザーをシステム管理者へ更新できない()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                put("/users/{id}", 6)
                    .with(user(instructor))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テストユーザー",
                        "departmentId": 1,
                        "roleId": 1
                    }
                    """)
            )
            .andExpect(
                status().isForbidden()
            )
            .andExpect(
                jsonPath("$.error")
                    .value("Forbidden")
            )
            .andExpect(
                jsonPath("$.message")
                    .value(
                        "この操作を実行する権限がありません"
                    )
            );
    }


    /*
     * =========================
     * 指導員
     * INSTRUCTORへ更新不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/update-not-allowed-role.sql")
    void 指導員はユーザーを指導員へ更新できない()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                put("/users/{id}", 7)
                    .with(user(instructor))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テストユーザー",
                        "departmentId": 1,
                        "roleId": 2
                    }
                    """)
            )
            .andExpect(
                status().isForbidden()
            )
            .andExpect(
                jsonPath("$.error")
                    .value("Forbidden")
            )
            .andExpect(
                jsonPath("$.message")
                    .value(
                        "この操作を実行する権限がありません"
                    )
            );
    }


    /*
     * =========================
     * 存在しないユーザー
     * 更新不可
     * =========================
     */

    @Test
    void 存在しないユーザーを更新できない()
            throws Exception {

        CustomUserDetails admin =
            createAdmin();

        mockMvc.perform(
                put("/users/{id}", 1)
                    .with(user(admin))
                    .contentType(
                        MediaType.APPLICATION_JSON
                    )
                    .content("""
                    {
                        "name": "テストユーザー",
                        "departmentId": 1,
                        "roleId": 2
                    }
                    """)
            )
            .andExpect(
                status().isNotFound()
            )
            .andExpect(
                jsonPath("$.error")
                    .value("NOT_FOUND")
            )
            .andExpect(
                jsonPath("$.message")
                    .value(
                        "更新対象ユーザーが存在しません"
                    )
            );
    }
}