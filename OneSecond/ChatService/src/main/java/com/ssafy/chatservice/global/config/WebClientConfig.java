package com.ssafy.chatservice.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final String INVITATION_SERVICE_URL = "http://invitation:8090"; // invitation-Service 직접 호출

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(INVITATION_SERVICE_URL)
                .build();
    }
}
