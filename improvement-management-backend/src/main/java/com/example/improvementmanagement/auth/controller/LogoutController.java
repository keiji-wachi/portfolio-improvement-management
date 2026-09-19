package com.example.improvementmanagement.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/logout")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class LogoutController {

    @PostMapping
    public ResponseEntity<Void> logout(
            HttpServletRequest request) {

        HttpSession session =
            request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return ResponseEntity.noContent().build();
    }
}