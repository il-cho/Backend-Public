package com.ssafy.chatservice.domain.hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class HelloController {
    @GetMapping("/hello")
    public Mono<String> hello() {
        log.debug("HelloController.hello() is called.");
        return Mono.just("Hello, World!");
    }
}
