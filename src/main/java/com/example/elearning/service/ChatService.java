package com.example.elearning.service;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.Chat;
import com.example.elearning.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    public Chat findById(Long id) throws ResourceNotFoundException {
        Chat chat =
                chatRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Chat not found on :: " + id));
        return chat;
    }

    public Chat update(Long id, Chat chat) throws ResourceNotFoundException {
        Chat old =
                chatRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Chat not found on :: " + id));

        old.setChatHistory(chat.getChatHistory());
        old.setPrimaryUser(chat.getPrimaryUser());
        old.setSecondaryUser(chat.getSecondaryUser());
        old.setLastSeen(chat.getLastSeen());
        old.setStatus(chat.isStatus());

        return save(old);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Chat chat =
                chatRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Chat not found on :: " + id));

        chatRepository.delete(chat);
    }
}
