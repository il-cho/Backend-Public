package com.ssafy.userservice.user.service;

import com.ssafy.userservice.user.controller.dto.request.ChatUsersRequest;
import com.ssafy.userservice.user.controller.dto.request.UserUpdateRequest;
import com.ssafy.userservice.user.controller.dto.response.ChatUsersResponse;
import com.ssafy.userservice.user.controller.dto.response.UserInformationResponse;


public interface UserService {
    UserInformationResponse searchUserById(int userId);
    UserInformationResponse updateUser(int id, UserUpdateRequest userUpdateRequest);
    void deleteUserById(int userId);
    ChatUsersResponse searchChatUserByIds(ChatUsersRequest request);
    void registUserWithIdAndName(int userId, String userName);
}
