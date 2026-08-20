package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserListDto;
import com.example.demo.Repository.UserListRepository;

@Service
public class UserListService {
    private final UserListRepository userListRepository;

    public UserListService(UserListRepository userListRepository){
        this.userListRepository = userListRepository;
    }

    public List<UserListDto> findAll(){
        return userListRepository.findAll();
    }
}
