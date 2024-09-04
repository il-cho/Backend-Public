package com.ssafy.invitationservice.invitation.adaptor.out.client;

import com.ssafy.invitationservice.invitation.adaptor.out.client.dto.ChatUsersRequest;
import com.ssafy.invitationservice.invitation.adaptor.out.client.dto.ChatUsersResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/user/chat")
    ChatUsersResponse chatUserList(@RequestBody ChatUsersRequest request);
}
