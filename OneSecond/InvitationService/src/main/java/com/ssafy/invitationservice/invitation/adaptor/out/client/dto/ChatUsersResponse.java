package com.ssafy.invitationservice.invitation.adaptor.out.client.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatUsersResponse {
    Map<Integer, List<String>> chatUserList;
}