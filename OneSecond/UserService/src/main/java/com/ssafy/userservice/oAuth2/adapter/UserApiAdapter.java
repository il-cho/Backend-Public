package com.ssafy.userservice.oAuth2.adapter;

public interface UserApiAdapter {
    void registUser(int userId, String userName);
    void deleteUser(int userId);
}
