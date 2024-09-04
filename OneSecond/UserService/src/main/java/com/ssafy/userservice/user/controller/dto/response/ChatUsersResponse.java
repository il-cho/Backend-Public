package com.ssafy.userservice.user.controller.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ChatUsersResponse {
    Map<Integer, String[]> chatUserList;
}