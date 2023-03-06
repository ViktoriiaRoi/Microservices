package com.example.facadeservice.service;

import com.example.facadeservice.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class FacadeServiceImpl implements FacadeService {

    WebClient loggingClient = WebClient.create("http://localhost:8081");
    WebClient messagesClient = WebClient.create("http://localhost:8082");

    @Override
    public Mono<String> getMessages() {
        Mono<String> loggingResponse = getResponse(loggingClient, "/logging-service");
        Mono<String> messagesResponse = getResponse(messagesClient, "/messages-service");

        return loggingResponse
                .zipWith(messagesResponse, (logging, messages) -> logging + " : " + messages)
                .onErrorReturn("One of services thrown error");
    }

    private Mono<String> getResponse(WebClient client, String uri) {
        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<Void> postMessage(String text) {
        Message message = new Message(text);
        return loggingClient.post()
                .uri("/logging-service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
