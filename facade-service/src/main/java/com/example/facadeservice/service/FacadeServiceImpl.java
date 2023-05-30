package com.example.facadeservice.service;

import com.example.domain.Message;
import com.example.facadeservice.repository.FacadeRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    private final DiscoveryClient discoveryClient;

    private final String LOGGING_SERVICE = "logging-service";
    private final String MESSAGES_SERVICE = "messages-service";

    public FacadeServiceImpl(FacadeRepository repository, DiscoveryClient discoveryClient) {
        this.repository = repository;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<String> getMessages() {
        var loggingService = getRandomService(LOGGING_SERVICE);
        var messagesService = getRandomService(MESSAGES_SERVICE);

        if (loggingService == null) return Mono.just("Logging service is unavailable now");
        if (messagesService == null) return Mono.just("Messages service is unavailable now");

        Mono<String> loggingResponse = getResponse(loggingService, "/logging-service");
        Mono<String> messagesResponse = getResponse(messagesService, "/messages-service");

        return loggingResponse
                .zipWith(messagesResponse, (logging, messages) -> String.format("Logging: %s\nMessages: %s", logging, messages))
                .onErrorReturn("One of services thrown error");
    }

    private Mono<String> getResponse(ServiceInstance service, String uri) {
        WebClient client = WebClient.create(service.getUri().toString());
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
        var loggingService = getRandomService(LOGGING_SERVICE);
        System.out.printf("Sent message %s to the logging service %d\n", message.getText(), loggingService.hashCode());

        WebClient client = WebClient.create(loggingService.getUri().toString());
        return client.post()
                .uri("/logging-service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private ServiceInstance getRandomService(String serviceId) {
        List<ServiceInstance> serviceList = discoveryClient.getInstances(serviceId);
        if (serviceList != null && serviceList.size() > 0 ) {
            return serviceList.get(random.nextInt(serviceList.size()));
        }
        return null;
    }
}
