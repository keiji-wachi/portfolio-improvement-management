package com.example.improvementmanagement.integration.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
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
class UserDeleteIntegrationTest {

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
     * ユーザー削除成功
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-success.sql")
    void システム管理者はユーザーを論理削除可能()
            throws Exception {

        CustomUserDetails admin =
            createAdmin();

        mockMvc.perform(
                delete("/users/{id}", 2)
                    .with(user(admin))
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                content().string("1")
            );

        Integer count =
            jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM user_mst
                WHERE id = ?
                AND is_deleted = TRUE
                """,
                Integer.class,
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
     * 自部署リリーフ削除成功
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-success.sql")
    void 指導員は自部署のリリーフユーザーを論理削除可能()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                delete("/users/{id}", 3)
                    .with(user(instructor))
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                content().string("1")
            );

        Integer count =
            jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM user_mst
                WHERE id = ?
                AND is_deleted = TRUE
                """,
                Integer.class,
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
     * 自部署作業者削除成功
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-success.sql")
    void 指導員は自部署の作業者ユーザーを論理削除可能()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                delete("/users/{id}", 4)
                    .with(user(instructor))
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                content().string("1")
            );

        Integer count =
            jdbcTemplate.queryForObject(
                """
                SELECT COUNT(*)
                FROM user_mst
                WHERE id = ?
                AND is_deleted = TRUE
                """,
                Integer.class,
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
     * 他部署ユーザー削除不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-other-dep.sql")
    void 指導員は他部署のユーザーを論理削除できない()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                delete("/users/{id}", 1)
                    .with(user(instructor))
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
     * 自分自身
     * 削除不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-myself.sql")
    void 自分自身を論理削除できない()
            throws Exception {

        CustomUserDetails admin =
            createAdmin();

        mockMvc.perform(
                delete("/users/{id}", 1)
                    .with(user(admin))
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
     * SYSTEM_ADMIN削除不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-not-allowed-role.sql")
    void 指導員はシステム管理者を論理削除できない()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                delete("/users/{id}", 2)
                    .with(user(instructor))
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
     * INSTRUCTOR削除不可
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/delete-not-allowed-role.sql")
    void 指導員は指導員を論理削除できない()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                delete("/users/{id}", 3)
                    .with(user(instructor))
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
     * 削除不可
     * =========================
     */

    @Test
    void 存在しないユーザーを論理削除できない()
            throws Exception {

        CustomUserDetails admin =
            createAdmin();

        mockMvc.perform(
                delete("/users/{id}", 1)
                    .with(user(admin))
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
                        "削除対象ユーザーが存在しません"
                    )
            );
    }
}