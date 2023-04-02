package com.example.loggingservice.service;

import com.example.loggingservice.repository.LoggingRepository;
import com.example.loggingservice.domain.Message;
import org.springframework.beans.factory.annotation.Qualifier;
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
