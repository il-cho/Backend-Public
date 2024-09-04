package com.ssafy.userservice.oAuth2.handler;

import com.ssafy.userservice.oAuth2.authUtil.CookieUtil;
import com.ssafy.userservice.oAuth2.authrepository.RefreshRedisRepository;
import com.ssafy.userservice.oAuth2.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshRedisRepository refreshRedisRepository;
    private final String bearer = "Bearer ";
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        deleteRefreshToken(request, response);
    }

    private void deleteRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        customOAuth2UserService.deleteToken(request);
        CookieUtil.deleteCookie(request, response, "RefreshToken");
    }

}
