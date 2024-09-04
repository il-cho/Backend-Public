package com.ssafy.userservice.oAuth2.handler;

import com.ssafy.userservice.oAuth2.adapter.UserApiAdapterImpl;
import com.ssafy.userservice.oAuth2.authUtil.CookieUtil;
import com.ssafy.userservice.oAuth2.authUtil.UnlinkUtil;
import com.ssafy.userservice.oAuth2.authrepository.CustomAuthorizationRequestRepository;
import com.ssafy.userservice.oAuth2.authrepository.RefreshRedisRepository;
import com.ssafy.userservice.oAuth2.service.CustomOAuth2UserService;
import com.ssafy.userservice.oAuth2.service.dto.output.OAuth2CustomUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomOauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UnlinkUtil unlinkUtil;
    private final RefreshRedisRepository refreshRedisRepository;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;
    private final UserApiAdapterImpl userInfoAdapter;
    private final CustomOAuth2UserService customOAuth2UserService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String mode = fetchModeFromRequest(request);
        Assert.hasText(mode, "mode must not be empty");
        String redirectUrl = fetchRedirectUrl(request, response, authentication, mode);
        if ("login".equalsIgnoreCase(mode)) {
            updateTokenAtCookie(response, authentication);
        } else if ("unlink".equalsIgnoreCase(mode)) {
            unlinkUser(request, response, authentication);
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, "https://il-cho.site" + redirectUrl);
    }

    private void unlinkUser(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2CustomUser user = (OAuth2CustomUser) authentication.getPrincipal();
        delete(request, response, user);
        unlinkUtil.unlink(user, customOAuth2UserService.getPlatform(user));
    }

    private String fetchRedirectUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String mode) throws IOException {
        if ("login".equalsIgnoreCase(mode)) {
            return "/login/success";
        } else if ("unlink".equalsIgnoreCase(mode)) {
            return "";
        }
        throw new IllegalArgumentException("No mode support");
    }

    private String fetchModeFromRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, "mode")
                .map(Cookie::getValue)
                .orElse("");
    }

    private void updateTokenAtCookie(HttpServletResponse response, Authentication authentication) throws IOException {
        Map<String, String> tokenInfo = customOAuth2UserService.updateLoginTokenInfo(authentication.getName());
        CookieUtil.addCookie(response, "AccessToken", tokenInfo.get("AccessToken"), 10800);
        CookieUtil.addCookie(response, "RefreshToken", tokenInfo.get("RefreshToken"), 2592000);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response, OAuth2CustomUser user) throws IOException {
        deleteInfo(deleteAuth(user));
        customOAuth2UserService.deleteToken(request);
    }

    private void deleteInfo(int userId) {
        userInfoAdapter.deleteUser(userId);
    }

    private int deleteAuth(OAuth2CustomUser user) {
        return customOAuth2UserService.deleteUser(user);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
