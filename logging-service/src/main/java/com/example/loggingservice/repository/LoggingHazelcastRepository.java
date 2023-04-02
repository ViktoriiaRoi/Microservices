package com.example.loggingservice.repository;

import com.example.loggingservice.domain.Message;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Primary
@Repository
public class LoggingHazelcastRepository implements LoggingRepository {

    HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    private final Map<UUID, String> messages = hzInstance.getMap("logging_map");

    @Override
    public String getMessages() {
        return messages.values().toString();
    }

    @Override
    public void addMessage(Message message) {
        messages.put(message.getId(), message.getText());
    }
}
