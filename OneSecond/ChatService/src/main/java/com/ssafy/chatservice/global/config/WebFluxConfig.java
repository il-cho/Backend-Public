package com.ssafy.chatservice.global.config;

import com.ssafy.chatservice.domain.chat.handler.CustomWebSocketHandler;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public SimpleUrlHandlerMapping handlerMapping(CustomWebSocketHandler handler) {
        return new SimpleUrlHandlerMapping(Map.of("/ws/**", handler));
    }
}
