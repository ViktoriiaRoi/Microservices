package com.example.loggingservice.controller;

import com.example.loggingservice.service.LoggingService;
import com.example.loggingservice.domain.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    private final LoggingService service;

    public LoggingController(LoggingService service) {
        this.service = service;
    }

    @GetMapping("/logging-service")
    public String getMessages() {
        return service.getMessages();
    }

    @PostMapping("/logging-service")
    public ResponseEntity<Void> postMessage(@RequestBody Message message) {
        System.out.println("Message added to storage: " + message.getText());
        service.addMessage(message);
        return ResponseEntity.ok().build();
    }

}