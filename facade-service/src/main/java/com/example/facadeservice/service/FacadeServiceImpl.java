package com.example.facadeservice.service;

import com.example.domain.Message;
import com.example.facadeservice.repository.FacadeRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
public class FacadeServiceImpl implements FacadeService {
    Random random = new Random();

    private final FacadeRepository repository;

    List<WebClient> loggingClientList = List.of(
            WebClient.create("http://localhost:8081"),
            WebClient.create("http://localhost:8082"),
            WebClient.create("http://localhost:8083")
    );

    List<WebClient> messagesClientList = List.of(
            WebClient.create("http://localhost:8084"),
            WebClient.create("http://localhost:8085")
    );

    public FacadeServiceImpl(FacadeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> getMessages() {
        var loggingClient = getRandomLoggingClient();
        var messagesClient = getRandomMessagesClient();

        Mono<String> loggingResponse = getResponse(loggingClient, "/logging-service");
        Mono<String> messagesResponse = getResponse(messagesClient, "/messages-service");

        return loggingResponse
                .zipWith(messagesResponse, (logging, messages) -> String.format("Logging: %s\nMessages: %s", logging, messages))
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
        Message message = Message.createMessage(text);

        Mono<Void> queueMono = postMessageToQueue(message);
        Mono<Void> loggingMono = postMessageToLogging(message);
        return Mono.when(queueMono, loggingMono);
    }

    private Mono<Void> postMessageToQueue(Message message) {
        return Mono.fromRunnable(() -> repository.postMessageToQueue(message));
    }

    private Mono<Void> postMessageToLogging(Message message) {
        var loggingClient = getRandomLoggingClient();
        System.out.printf("Sent message %s to the logging service %d\n", message.getText(), loggingClient.hashCode());

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

    private WebClient getRandomMessagesClient() {
        return messagesClientList.get(random.nextInt(messagesClientList.size()));
    }
}
