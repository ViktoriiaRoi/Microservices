package com.example.loggingservice.service;

import com.example.loggingservice.repository.LoggingInMemRepository;
import com.example.loggingservice.repository.LoggingRepository;
import com.example.loggingservice.domain.Message;

public class LoggingServiceImpl implements LoggingService {

    private final LoggingRepository repository = new LoggingInMemRepository();

    @Override
    public String getMessages() {
        return repository.getMessages();
    }

    @Override
    public void addMessage(Message message) {
        repository.addMessage(message);
    }
}
