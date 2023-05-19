package com.example.messagesservice.service;

import com.example.messagesservice.repository.MessagesRepository;
import org.springframework.stereotype.Service;

@Service
public class MessagesServiceImpl implements MessagesService {

    private final MessagesRepository repository;

    public MessagesServiceImpl(MessagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribeOnQueue() {
        repository.subscribeOnQueue();
    }

    @Override
    public String getMessages() {
        return repository.getMessages();
    }
}
