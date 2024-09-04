package com.ssafy.invitationservice.invitation.adaptor.out.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatUsersRequest {
    List<Integer> chatUserList;
}

