package com.example.loggingservice.controller;

import com.example.loggingservice.service.LoggingService;
import com.example.loggingservice.service.LoggingServiceImpl;
import com.example.loggingservice.domain.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    private final LoggingService service = new LoggingServiceImpl();

    @GetMapping("/logging-service")
    public String getMessages() {
        return service.getMessages();
    }

    @PostMapping("/logging-service")
    public void postMessage(@RequestBody Message message) {
        if (message == null || message.isEmpty()) {
            System.out.println("Received empty message");
            return;
        }

        System.out.println("Message added to local storage: " + message.getText());
        service.addMessage(message);
    }

}