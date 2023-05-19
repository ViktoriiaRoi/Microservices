package com.example.messagesservice.controller;

import com.example.messagesservice.service.MessagesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    private final MessagesService service;

    public MessagesController(MessagesService service) {
        this.service = service;
        service.subscribeOnQueue();
    }

    @GetMapping("/messages-service")
    public String getMessages() {
        return service.getMessages();
    }
}