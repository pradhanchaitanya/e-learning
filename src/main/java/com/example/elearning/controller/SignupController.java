package com.example.elearning.controller;

import com.example.elearning.model.Register;
import com.example.elearning.model.User;
import com.example.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Boolean> registerUser(@RequestBody Register registerDto) {
        User registerUser = new User();
        registerUser.setUsername(registerDto.getUsername());
        registerUser.setEmail(registerDto.getEmail());
        registerUser.setPassword(registerDto.getPassword());
        registerUser.setMobileNumber(registerDto.getMobileNumber());
        registerUser.setQualification(registerDto.getQualification());
        registerUser.setRole(registerDto.getRole().toUpperCase());
        registerUser.setActive(Boolean.FALSE);

        User registeredUser = userService.save(registerUser);
        if (registeredUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Boolean.TRUE);
        } else {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}
