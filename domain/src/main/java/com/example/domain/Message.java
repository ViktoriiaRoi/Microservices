package com.example.domain;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
    private UUID id;
    private String text;

    public Message() {}

    public Message(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    public static Message createMessage(String text) {
        return new Message(UUID.randomUUID(), text);
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
