package com.example.elearning.controller;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.Resource;
import com.example.elearning.model.User;
import com.example.elearning.service.ResourceService;
import com.example.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /*
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = null;
        try {
            user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    */

    @PostMapping("/verify/{id}")
    public ResponseEntity<String> verifyUser(@PathVariable Long id) {
        try {
            userService.verifyUser(id);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Verified");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
    @PutMapping("{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        User u = null;
        try {
            u = userService.update(id, user);
            return ResponseEntity.ok(u);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    */


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User deleted");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/resource/{id}")
    public ResponseEntity<String> updateResource(@PathVariable Long id, Resource resource) {
        try {
            resourceService.update(id, resource);
            return ResponseEntity.ok("Updated Success");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
