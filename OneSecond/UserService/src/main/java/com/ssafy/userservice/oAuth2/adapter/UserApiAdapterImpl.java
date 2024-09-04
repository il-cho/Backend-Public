package com.ssafy.userservice.oAuth2.adapter;

import com.ssafy.userservice.user.controller.UserController;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApiAdapterImpl implements UserApiAdapter{

    private final UserController userController;

    @Override
    public void registUser(int userId, String userName) {
        userController.registUser(userId, userName);
    }

    @Override
    public void deleteUser(int userId) {
        userController.deleteUser(userId);
    }
}
