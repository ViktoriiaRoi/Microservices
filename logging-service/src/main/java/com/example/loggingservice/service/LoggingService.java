package com.example.loggingservice.service;

import com.example.domain.Message;

public interface LoggingService {
    String getMessages();

    void addMessage(Message message);
}
