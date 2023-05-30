package com.example.facadeservice.controller;

import com.example.facadeservice.service.FacadeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FacadeController {
    private final FacadeService service;

    public FacadeController(FacadeService service) {
        this.service = service;
    }

    @GetMapping("/facade-service")
    public Mono<String> getMessages() {
        return service.getMessages();
    }

    @PostMapping("/facade-service")
    public Mono<Void> postMessage(@RequestBody String text) {
        if (text.isEmpty()) {
            System.out.println("Processed empty message");
            return Mono.error(new IllegalArgumentException("Message cannot be empty"));
        }
        return service.postMessage(text);
    }

    @GetMapping("/health")
    private String healthCheck(){
        return "OK";
    }
}
