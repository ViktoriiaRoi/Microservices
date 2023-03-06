package com.example.facadeservice.domain;

import java.util.UUID;

public class Message {
    private final UUID id;
    private final String text;

    public Message(String text) {
        this.id = UUID.randomUUID();
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
}
