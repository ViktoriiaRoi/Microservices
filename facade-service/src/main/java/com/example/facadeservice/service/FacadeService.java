package com.example.facadeservice.service;

import reactor.core.publisher.Mono;

public interface FacadeService {
    Mono<String> getMessages();
    Mono<Void> postMessage(String text);
}
