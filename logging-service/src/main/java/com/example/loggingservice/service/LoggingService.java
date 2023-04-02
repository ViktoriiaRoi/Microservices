package com.example.loggingservice.service;

import com.example.loggingservice.domain.Message;
import org.springframework.stereotype.Service;

public interface LoggingService {
    String getMessages();

    void addMessage(Message message);
}
