package com.ssafy.invitationservice.invitation.adaptor.out.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "chat-service")
public interface ChatClient {


}
