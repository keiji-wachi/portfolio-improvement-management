package com.example.improvementmanagement.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.improvementmanagement.auth.security.CustomUserDetails;
import com.example.improvementmanagement.user.dto.CreateUserDto;
import com.example.improvementmanagement.user.dto.UserListDto;
import com.example.improvementmanagement.user.dto.UserUpdateDto;
import com.example.improvementmanagement.user.service.UserCreateService;
import com.example.improvementmanagement.user.service.UserDeleteService;
import com.example.improvementmanagement.user.service.UserListService;
import com.example.improvementmanagement.user.service.UserUpdateService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

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
    public int createUser(@Valid @RequestBody CreateUserDto dto,@AuthenticationPrincipal CustomUserDetails loginUser) {
        return userCreateService.createUser(dto, loginUser);
    }

    @GetMapping
    public List<UserListDto> getUser(@AuthenticationPrincipal CustomUserDetails loginUser) {

        return userListService.findAll(loginUser);
    }

    @DeleteMapping("/{id}")
    public int deleteUser(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails loginUser) {
        return userDeleteService.deleteUser(id, loginUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer id,@Valid @RequestBody UserUpdateDto dto,@AuthenticationPrincipal CustomUserDetails loginUser) {
        userUpdateService.updateUser(loginUser, id, dto);

        return ResponseEntity.ok().build();
    }
    
}
