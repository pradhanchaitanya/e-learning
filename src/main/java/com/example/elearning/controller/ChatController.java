package com.example.elearning.controller;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.Chat;
import com.example.elearning.model.User;
import com.example.elearning.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        Chat chat = null;
        try {
            chat = chatService.findById(id);
            return ResponseEntity.ok(chat);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Chat> saveChat(@RequestBody Chat chat) {
        Chat c = chatService.save(chat);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }

    @PutMapping("{id}")
    public ResponseEntity<Chat> editChat(@PathVariable Long id, @RequestBody Chat chat) {
        Chat c = null;
        try {
            c = chatService.update(id, chat);
            return ResponseEntity.ok(c);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteChat(@PathVariable Long id) {
        try {
            chatService.delete(id);
            return ResponseEntity.ok(Boolean.TRUE);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}
