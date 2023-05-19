package com.example.messagesservice.repository;

import com.example.domain.Message;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MessagesRepositoryImpl implements MessagesRepository {

    HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    private final Map<UUID, Message> messages = new ConcurrentHashMap<>();

    @Override
    public void subscribeOnQueue() {
        IQueue<Message> queue = hzInstance.getQueue("message_queue");
        Thread messageListenerThread = new Thread(() -> {
            while (true) {
                try {
                    Message message = queue.take();
                    addMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        messageListenerThread.start();
    }

    private void addMessage(Message message) {
        messages.put(message.getId(), message);
        System.out.println("Message added to storage: " + message.getText());
    }

    @Override
    public String getMessages() {
        return messages.values().toString();
    }
}
