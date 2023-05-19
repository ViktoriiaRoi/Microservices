package com.example.loggingservice.repository;

import com.example.domain.Message;

public interface LoggingRepository {
    String getMessages();

    void addMessage(Message message);
}
