package com.terraformgui.backend.service;

import com.terraformgui.backend.dto.UserRequestDto;
import com.terraformgui.backend.entity.User;
import com.terraformgui.backend.exception.InvalidCredentialsException;
import com.terraformgui.backend.exception.UserAlreadyExistsException;
import com.terraformgui.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(UserRequestDto user) {
        if (userRepository.findByUsername(user.username()).isPresent()) {
            try {
                throw new UserAlreadyExistsException("User already exists");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        User newUser = new User();
        newUser.setUsername(user.username());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        return userRepository.save(newUser);
    }

    public User authenticate(String username,String password){
        User user = userRepository.findByUsername(username).orElseThrow(()->
                new InvalidCredentialsException("Invalid user name or password")
        );
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return user;
    }
}
