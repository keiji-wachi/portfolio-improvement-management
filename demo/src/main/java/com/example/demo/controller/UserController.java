package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.CreateUserDto;
import com.example.demo.Dto.LoginResponseDto;
import com.example.demo.Dto.UserListDto;
import com.example.demo.Dto.UserUpdateDto;
import com.example.demo.Service.UserCreateService;
import com.example.demo.Service.UserDeleteService;
import com.example.demo.Service.UserListService;
import com.example.demo.Service.UserUpdateService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class UserController {

    private final UserCreateService userCreateService;
    private final UserListService userListService;
    private final UserDeleteService userDeleteService;
    private final UserUpdateService userUpdateService;

    public UserController(UserCreateService userCreateService, UserListService userListService, UserDeleteService userDeleteService, UserUpdateService userUpdateService){
        this.userCreateService = userCreateService;
        this.userListService = userListService;
        this.userDeleteService = userDeleteService;
        this.userUpdateService = userUpdateService;
    }

    @PostMapping
    public int createUser(@Valid @RequestBody CreateUserDto dto,HttpSession session) {
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        return userCreateService.createUser(dto, loginUser);
    }

    @GetMapping
    public List<UserListDto> getUser() {
        return userListService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        userDeleteService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public int userUpdate(@PathVariable int id, @RequestBody UserUpdateDto dto) {
        return userUpdateService.userUpdate(id, dto);
    }
    
}
