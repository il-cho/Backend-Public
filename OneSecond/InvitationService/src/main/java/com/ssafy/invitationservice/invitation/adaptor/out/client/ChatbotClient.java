package com.ssafy.invitationservice.invitation.adaptor.out.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "chatbot-service")
public interface ChatbotClient {


}
