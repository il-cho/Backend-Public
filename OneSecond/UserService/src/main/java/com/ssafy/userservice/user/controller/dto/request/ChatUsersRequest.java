package com.ssafy.userservice.user.controller.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatUsersRequest {
    List<Integer> chatUserList;
}
