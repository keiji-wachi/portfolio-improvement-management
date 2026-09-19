package com.example.improvementmanagement.integration.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/integration/testdata/auth/login-success.sql")
    void 正しい社員番号とパスワードでログインできる() throws Exception {

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "employeeNo": "test0001",
                      "password": "test1234"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.departmentId").value(1))
            .andExpect(jsonPath("$.roleId").value(1))
            .andExpect(jsonPath("$.firstLoginFlag").value(false))
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test 
    void 存在しない社員番号でログインできない() throws Exception{

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "employeeNo": "NOT_EXIST",
                  "password": "test1234"
                }
                """))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401))
            .andExpect(jsonPath("$.error").value("Unauthorized"))
            .andExpect(jsonPath("$.message").value("社員番号またはパスワードが正しくありません"));
    }

    @Test
    @Sql("/integration/testdata/auth/login-success.sql")
    void パスワード不一致ではログインできない() throws Exception {

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "employeeNo": "test0001",
                  "password": "bad-password"
                }
                """))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status").value(401))
            .andExpect(jsonPath("$.error").value("Unauthorized"))
            .andExpect(jsonPath("$.message").value("社員番号またはパスワードが正しくありません"));
    }

    @Test
    @Sql("/integration/testdata/auth/login-deleted-user.sql")
    void 論理削除済みユーザーはログインできない() throws Exception {

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                  "employeeNo": "test0001",
                  "password": "test1234"
                }
                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.message").value("社員番号またはパスワードが正しくありません"));
    }
}

