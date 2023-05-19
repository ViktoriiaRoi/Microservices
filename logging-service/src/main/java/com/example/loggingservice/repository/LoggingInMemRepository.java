package com.example.loggingservice.repository;

import com.example.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LoggingInMemRepository implements LoggingRepository {

    private final Map<UUID, Message> messages = new ConcurrentHashMap<>();

    @Override
    public String getMessages() {
        return messages.values().toString();
    }

    @Override
    public void addMessage(Message message) {
        messages.put(message.getId(), message);
    }
}
