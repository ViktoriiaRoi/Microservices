package com.example.loggingservice.service;

import com.example.domain.Message;
import com.example.loggingservice.repository.LoggingRepository;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService {

    private final LoggingRepository repository;

    public LoggingServiceImpl(LoggingRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getMessages() {
        return repository.getMessages();
    }

    @Override
    public void addMessage(Message message) {
        repository.addMessage(message);
    }
}
