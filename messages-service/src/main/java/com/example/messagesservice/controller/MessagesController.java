package com.example.messagesservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    @GetMapping("/messages-service")
    public String getMessages() {
        return "messages-service is not implemented yet";
    }

}