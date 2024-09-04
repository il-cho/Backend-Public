package com.ssafy.userservice.user.controller;

import com.ssafy.userservice.global.error.ErrorCode;
import com.ssafy.userservice.global.error.exception.AuthException;
import com.ssafy.userservice.user.controller.dto.request.ChatUsersRequest;
import com.ssafy.userservice.user.controller.dto.request.UserUpdateRequest;
import com.ssafy.userservice.user.controller.dto.response.ChatUsersResponse;
import com.ssafy.userservice.user.controller.dto.response.UserInformationResponse;
import com.ssafy.userservice.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserInformationResponse findUserById(HttpServletRequest request) {
        String loginId = request.getHeader("loginId");
        log.info("loginId: {}", loginId);
        if(!StringUtils.hasText(loginId)) throw new AuthException(ErrorCode.NeedLogin);
        return userService.searchUserById(Integer.parseInt(loginId));
    }

    @PatchMapping
    public UserInformationResponse updateUser(HttpServletRequest request, @RequestBody UserUpdateRequest req) {
        String loginId = request.getHeader("loginId");
        if(!StringUtils.hasText(loginId)) throw new AuthException(ErrorCode.NeedLogin);
        return userService.updateUser(Integer.parseInt(loginId), req);
    }

    @PostMapping("/chat")
    public ChatUsersResponse chatUserNicknameMap(@RequestBody ChatUsersRequest request) {
        return userService.searchChatUserByIds(request);
    }

    public void registUser(int userId, String nickname) {
        userService.registUserWithIdAndName(userId, nickname);
    }

    public void deleteUser(int userId) {
        userService.deleteUserById(userId);
    }


}
