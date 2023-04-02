package com.example.facadeservice.service;

import com.example.facadeservice.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
public class FacadeServiceImpl implements FacadeService {
    Random random = new Random();

    WebClient messagesClient = WebClient.create("http://localhost:8081");
    List<WebClient> loggingClientList = List.of(
            WebClient.create("http://localhost:8082"),
            WebClient.create("http://localhost:8083"),
            WebClient.create("http://localhost:8084")
    );


    @Override
    public Mono<String> getMessages() {
        var loggingClient = getRandomLoggingClient();

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
        var loggingClient = getRandomLoggingClient();
        System.out.printf("Sent message %s to the service %s\n", text, loggingClient);

        return loggingClient.post()
                .uri("/logging-service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private WebClient getRandomLoggingClient() {
        return loggingClientList.get(random.nextInt(loggingClientList.size()));
    }
}
