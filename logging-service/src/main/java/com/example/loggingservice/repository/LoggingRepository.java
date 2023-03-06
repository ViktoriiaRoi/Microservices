package com.example.loggingservice.repository;

import com.example.loggingservice.domain.Message;

public interface LoggingRepository {
    String getMessages();

    void addMessage(Message message);
}
