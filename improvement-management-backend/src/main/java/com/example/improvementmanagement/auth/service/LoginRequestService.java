package com.example.improvementmanagement.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.dto.LoginRequestDto;
import com.example.improvementmanagement.auth.dto.LoginResponseDto;
import com.example.improvementmanagement.auth.security.CustomUserDetails;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Service
public class LoginRequestService {

    private final AuthenticationManager authenticationManager;

    public LoginRequestService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDto loginAuth(
        LoginRequestDto dto,
        HttpServletRequest request,
        HttpServletResponse response) {

    try {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                dto.getEmployeeNo(),
                                dto.getPassWord()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = request.getSession(true);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return new LoginResponseDto(
                userDetails.getUserId(),
                userDetails.getDepartmentId(),
                userDetails.getRoleId(),
                userDetails.getFirstLoginFlag(),
                true
        );

    } catch (BadCredentialsException e) {

        return new LoginResponseDto(
                null,
                null,
                null,
                false,
                false
        );
    }
    }  
}