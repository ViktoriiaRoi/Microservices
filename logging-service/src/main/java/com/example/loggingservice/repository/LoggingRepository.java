package com.example.loggingservice.repository;

import com.example.loggingservice.domain.Message;
import org.springframework.stereotype.Repository;

public interface LoggingRepository {
    String getMessages();

    void addMessage(Message message);
}
