package com.example.demo.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.LoginRequestDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.LoginUserDto;
import com.example.demo.Repository.LoginRequestRepository;

@Service
public class LoginRequestService {

    private final LoginRequestRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LoginRequestService(LoginRequestRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto loginAuth(LoginRequestDto dto) {

        LoginUserDto user = repository.findByEmployeeNo(dto.getEmployeeNo());

        if (user == null) {
            // employeeNoが存在しない
            return new LoginResponseDto(null, null, null, false, false);
        }

    boolean passwordMatches = passwordEncoder.matches(dto.getPassWord(),user.getPasswordHash());

    if (!passwordMatches) {
        return new LoginResponseDto(null, null, null, false, false);
    }

    return new LoginResponseDto(user.getUserId(),user.getDepartmentId(),user.getRoleId(),user.getFirstLoginFlag(),true);

    }
}