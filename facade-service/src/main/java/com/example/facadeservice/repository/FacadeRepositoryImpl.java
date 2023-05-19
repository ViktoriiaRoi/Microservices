package com.example.facadeservice.repository;

import com.example.domain.Message;
import com.hazelcast.collection.IQueue;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.stereotype.Repository;

@Repository
public class FacadeRepositoryImpl implements FacadeRepository {

    HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance();
    IQueue<Message> queue = hzInstance.getQueue("message_queue");

    @Override
    public void postMessageToQueue(Message message) {
        try {
            queue.put(message);
            System.out.printf("Added message %s to the queue\n", message.getText());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
