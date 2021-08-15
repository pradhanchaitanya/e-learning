package com.example.elearning.service;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.User;
import com.example.elearning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User save(User user) {
        String rawPwd = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPwd));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws ResourceNotFoundException {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
        return user;
    }

    public User findByUsername(String username) throws ResourceNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + username));
        return user;
    }

    public User update(Long id, User user) throws ResourceNotFoundException {
        User old =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));

        old.setEmail(user.getEmail());
        old.setUsername(user.getUsername());
        old.setPassword(user.getPassword());
        old.setMobileNumber(user.getMobileNumber());
        old.setQualification(user.getQualification());
        old.setActive(user.isActive());
        old.setRole(user.getRole());

        return save(old);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));

        userRepository.delete(user);
    }

    public void verifyUser(Long id) throws ResourceNotFoundException {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));

        user.setActive(Boolean.TRUE);
        update(id, user);
    }
}