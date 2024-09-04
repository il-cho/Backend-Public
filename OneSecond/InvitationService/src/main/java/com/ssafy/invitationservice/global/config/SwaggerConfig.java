package com.ssafy.invitationservice.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "InvitationService REST API",
                version = "1.0.0",
                description = "spring doc을 사용한 InvitationService API"
        ),
        servers = @Server(url = "/")
)
@Component
public class SwaggerConfig {

}