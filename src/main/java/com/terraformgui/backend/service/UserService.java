package com.terraformgui.backend.service;

import com.terraformgui.backend.dto.UserRequestDto;
import com.terraformgui.backend.entity.User;
import com.terraformgui.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(UserRequestDto user){
        if(userRepository.findByUsername(user.username()).isPresent()){
            try {
                throw new Exception("User already exists");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        User newUser = new User();
        newUser.setUsername(user.username());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        return userRepository.save(newUser);
    }
}
