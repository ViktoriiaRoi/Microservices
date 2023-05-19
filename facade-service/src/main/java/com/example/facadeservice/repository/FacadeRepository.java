package com.example.facadeservice.repository;

import com.example.domain.Message;

public interface FacadeRepository {
    void postMessageToQueue(Message message);
}
