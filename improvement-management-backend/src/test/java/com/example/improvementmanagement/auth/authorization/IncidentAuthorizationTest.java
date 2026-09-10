package com.example.improvementmanagement.auth.authorization;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.auth.security.authorization.IncidentAuthorization;

@ExtendWith(MockitoExtension.class)
class IncidentAuthorizationTest {

    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails loginUser;

    private IncidentAuthorization incidentAuthorization;

    @BeforeEach
    void setUp() {

        incidentAuthorization = new IncidentAuthorization();

        when(authentication.getPrincipal())
                .thenReturn(loginUser);
    }

    @Test
    void 指導員は異常対応入力できる() {

        when(loginUser.getRoleId())
                .thenReturn(2);

        boolean result =
                incidentAuthorization.canCreate(authentication);

        assertTrue(result);
    }

    @Test
    void リリーフは異常対応入力できる() {

        when(loginUser.getRoleId())
                .thenReturn(3);

        boolean result =
                incidentAuthorization.canCreate(authentication);

        assertTrue(result);
    }

    @Test
    void システム管理者は異常対応入力できない() {

        when(loginUser.getRoleId())
                .thenReturn(1);

        boolean result =
                incidentAuthorization.canCreate(authentication);

        assertFalse(result);
    }

    @Test
    void 作業者は異常対応入力できない() {

        when(loginUser.getRoleId())
                .thenReturn(4);

        boolean result =
                incidentAuthorization.canCreate(authentication);

        assertFalse(result);
    }

    @Test
    void 指導員は異常対応記録を閲覧できる() {

        when(loginUser.getRoleId()).thenReturn(2);

        assertTrue(
            incidentAuthorization.canGet(authentication)
        );
    }

    @Test
    void リリーフは異常対応記録を閲覧できる() {

        when(loginUser.getRoleId()).thenReturn(3);

        assertTrue(
            incidentAuthorization.canGet(authentication)
        );
    }

    @Test
    void 作業者は異常対応記録を閲覧できる() {

        when(loginUser.getRoleId()).thenReturn(4);

        assertTrue(
            incidentAuthorization.canGet(authentication)
        );
    }

    @Test
    void システム管理者は異常対応記録を閲覧できない() {

        when(loginUser.getRoleId()).thenReturn(1);

        assertFalse(
            incidentAuthorization.canGet(authentication)
        );
    }
}