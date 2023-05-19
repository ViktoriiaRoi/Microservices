package com.example.messagesservice.repository;

public interface MessagesRepository {
    String getMessages();

    void subscribeOnQueue();
}
