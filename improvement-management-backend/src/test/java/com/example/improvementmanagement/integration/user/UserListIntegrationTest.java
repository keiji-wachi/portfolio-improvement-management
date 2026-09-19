package com.example.improvementmanagement.integration.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.improvementmanagement.auth.security.CustomUserDetails;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


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
     * 全件取得
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/list-success.sql")
    void システム管理者は論理削除されていないユーザーを全件取得可能()
            throws Exception {

        CustomUserDetails admin =
            createAdmin();

        mockMvc.perform(
                get("/users")
                    .with(user(admin))
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                jsonPath("$.length()")
                    .value(3)
            )
            .andExpect(
                jsonPath(
                    "$[?(@.employeeNumber == 'test0002')]"
                ).isEmpty()
            );
    }


    /*
     * =========================
     * 指導員
     * 自部署のみ取得
     * =========================
     */

    @Test
    @Sql("/integration/testdata/user/list-success.sql")
    void 指導員は論理削除されていない自部署ユーザーを全件取得可能()
            throws Exception {

        CustomUserDetails instructor =
            createInstructor();

        mockMvc.perform(
                get("/users")
                    .with(user(instructor))
            )
            .andExpect(
                status().isOk()
            )
            .andExpect(
                jsonPath("$.length()")
                    .value(2)
            )
            .andExpect(
                jsonPath(
                    "$[?(@.employeeNumber == 'test0002')]"
                ).isEmpty()
            )
            .andExpect(
                jsonPath(
                    "$[?(@.employeeNumber == 'test0004')]"
                ).isEmpty()
            );
    }
}