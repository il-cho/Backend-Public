package com.ssafy.userservice.user.service.impl;

import com.ssafy.userservice.global.error.ErrorCode;
import com.ssafy.userservice.global.error.exception.UserException;
import com.ssafy.userservice.user.controller.dto.request.ChatUsersRequest;
import com.ssafy.userservice.user.controller.dto.request.UserUpdateRequest;
import com.ssafy.userservice.user.controller.dto.response.ChatUsersResponse;
import com.ssafy.userservice.user.controller.dto.response.UserInformationResponse;
import com.ssafy.userservice.user.entity.UserInformation;
import com.ssafy.userservice.user.repository.UserInformationRepository;
import com.ssafy.userservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserInformationRepository userInfoRepository;

    @Override
    public UserInformationResponse searchUserById(int userId) {
        UserInformation userInfo = userInfoRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.NoUser));
        log.info("searchUserNameAndDate: {} {}", userInfo.getNickname(), userInfo.getRegistDate());
        return new UserInformationResponse(String.valueOf(userInfo.getId()), userInfo.getProfile(), userInfo.getNickname());
    }

    @Override
    public UserInformationResponse updateUser(int id, UserUpdateRequest userUpdateRequest) {
        UserInformation userInfo = userInfoRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.NoUser));
        if(userUpdateRequest.getNickname() != null) {
            userInfo.setNickname(userUpdateRequest.getNickname());
        }
        if(userUpdateRequest.getProfile() != 0) {
            userInfo.setProfile(userUpdateRequest.getProfile());
        }
        userInfo.updated();
        userInfoRepository.save(userInfo);
        return new UserInformationResponse(String.valueOf(userInfo.getId()), userInfo.getProfile(), userInfo.getNickname());
    }

    @Override
    public ChatUsersResponse searchChatUserByIds(ChatUsersRequest request) {
        List<UserInformation> list = userInfoRepository.findAllById(request.getChatUserList());
        Map<Integer, String[]> chatUserResult = new HashMap<>();
        for(UserInformation userInfo : list) {
            chatUserResult.put(userInfo.getId(), new String[]{userInfo.getNickname(), String.valueOf(userInfo.getProfile())});
        }
        return new ChatUsersResponse(chatUserResult);
    }

    @Override
    public void registUserWithIdAndName(int userId, String userName) {
        userInfoRepository.save(UserInformation.createInstance(userId, userName));
    }

    @Override
    public void deleteUserById(int userId) {
        userInfoRepository.deleteById(userId);
    }

}
