package com.example.improvementmanagement.user.service;

import org.springframework.stereotype.Service;

import com.example.improvementmanagement.user.dto.UserDetailDto;
import com.example.improvementmanagement.user.repository.UserGetRepository;

@Service
public class UserGetService {

    private final UserGetRepository userGetRepository;

    public UserGetService(UserGetRepository userGetRepository) {
        this.userGetRepository = userGetRepository;
    }

    public UserDetailDto getUser(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ユーザーIDが不正です");
        }

        return userGetRepository.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("対象ユーザーが存在しません")
                );
    }
}