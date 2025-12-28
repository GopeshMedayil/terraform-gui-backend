package com.terraformgui.backend.controller;

import com.terraformgui.backend.dto.UserRequestDto;
import com.terraformgui.backend.dto.UserResponseDto;
import com.terraformgui.backend.entity.User;
import com.terraformgui.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @PostMapping("/login")
    public String login(){
        return "Login successfully";
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto user){
        User createUser = userService.registerUser(user);
        UserResponseDto response = new UserResponseDto(createUser.getUsername(),createUser.getId());
        return ResponseEntity.ok(response);
    }
}
