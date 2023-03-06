package com.example.facadeservice.controller;

import com.example.facadeservice.service.FacadeService;
import com.example.facadeservice.service.FacadeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FacadeController {
    private final FacadeService service = new FacadeServiceImpl();

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

        System.out.println("Message is processed: " + text);
        return service.postMessage(text);
    }

}
