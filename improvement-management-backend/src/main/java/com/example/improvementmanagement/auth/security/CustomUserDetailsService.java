package com.example.improvementmanagement.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.improvementmanagement.auth.dto.LoginUserDto;
import com.example.improvementmanagement.auth.repository.LoginRequestRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginRequestRepository loginRequestRepository;

    public CustomUserDetailsService(LoginRequestRepository loginRequestRepository) {
        this.loginRequestRepository = loginRequestRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String employeeNo)throws UsernameNotFoundException {
        LoginUserDto user = loginRequestRepository.findByEmployeeNo(employeeNo);

        if (user == null) {
            throw new UsernameNotFoundException("ユーザーが存在しません: " + employeeNo);
        }

        return new CustomUserDetails(
            user.getUserId(),
            user.getEmployeeNo(),
            user.getDepartmentId(),
            user.getRoleId(),
            user.getPasswordHash(),
            user.getFirstLoginFlag()
        );
    }
}
    
