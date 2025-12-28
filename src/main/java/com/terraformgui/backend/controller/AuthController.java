package com.terraformgui.backend.controller;

import com.terraformgui.backend.dto.LoginResponseDto;
import com.terraformgui.backend.dto.UserRequestDto;
import com.terraformgui.backend.dto.UserResponseDto;
import com.terraformgui.backend.entity.User;
import com.terraformgui.backend.service.JwtUtil;
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
    @Autowired
    JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody UserRequestDto request){
        // 1. Authenticate user
        User user = userService.authenticate(
                request.username(),
                request.password()
        );

        // 2. Generate tokens
        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // 3. Return response
        return ResponseEntity.ok(
                new LoginResponseDto(accessToken, refreshToken)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto user){
        User createUser = userService.registerUser(user);
        UserResponseDto response = new UserResponseDto(createUser.getUsername(),createUser.getId());
        return ResponseEntity.ok(response);
    }
}
