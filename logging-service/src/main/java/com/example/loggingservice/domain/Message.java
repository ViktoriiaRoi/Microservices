package com.example.loggingservice.domain;

import java.util.UUID;

public class Message {
    private final UUID id;
    private final String text;

    public Message(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isEmpty() {
        return text.isEmpty();
    }
}
