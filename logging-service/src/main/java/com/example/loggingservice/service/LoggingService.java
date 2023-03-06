package com.example.loggingservice.service;

import com.example.loggingservice.domain.Message;

public interface LoggingService {
    String getMessages();

    void addMessage(Message message);
}
