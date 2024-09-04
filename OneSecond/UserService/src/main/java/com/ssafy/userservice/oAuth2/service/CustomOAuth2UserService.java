package com.ssafy.userservice.oAuth2.service;

import com.ssafy.userservice.global.error.ErrorCode;
import com.ssafy.userservice.global.error.exception.AuthException;
import com.ssafy.userservice.global.error.exception.UserException;
import com.ssafy.userservice.oAuth2.adapter.UserApiAdapterImpl;
import com.ssafy.userservice.oAuth2.authUtil.CookieUtil;
import com.ssafy.userservice.oAuth2.authUtil.RefreshUtil;
import com.ssafy.userservice.oAuth2.service.dto.input.OAuth2UserInput;
import com.ssafy.userservice.oAuth2.service.dto.input.OAuth2UserInputFactory;
import com.ssafy.userservice.oAuth2.service.dto.output.OAuth2CustomUser;
import com.ssafy.userservice.user.entity.KakaoUser;
import com.ssafy.userservice.user.entity.NaverUser;
import com.ssafy.userservice.user.entity.User;
import com.ssafy.userservice.user.enumeration.OAuth2Platform;
import com.ssafy.userservice.user.repository.KakaoUserRepository;
import com.ssafy.userservice.user.repository.NaverUserRepository;
import com.ssafy.userservice.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final KakaoUserRepository kakaoUserRepository;
    private final NaverUserRepository naverUserRepository;
    private final UserApiAdapterImpl userApiAdapter;

    private final RefreshUtil refreshUtil;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User loadedUser =  super.loadUser(userRequest);
        log.info("loaded user: {}", loadedUser.getName());
        try {
            return processUser(userRequest, loadedUser);
        } catch (AuthenticationException ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }

    }

    private OAuth2User processUser(OAuth2UserRequest userRequest, OAuth2User loadedUser) {
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        return checkUser(OAuth2UserInputFactory.getOAuth2UserInput(registrationId, accessToken, loadedUser.getAttributes()));

    }

    private OAuth2User checkUser(OAuth2UserInput oAuth2UserInput) {
        OAuth2Platform platform = oAuth2UserInput.getPlatform();
        log.info("input user: {}", oAuth2UserInput.getId());
        int userId;
        if(OAuth2Platform.KAKAO.equals(platform)){
            userId = kakaoUserRepository.findById(oAuth2UserInput.getId()).orElseGet(() -> registKakaoUser(oAuth2UserInput)).getUserId();
        } else if(OAuth2Platform.NAVER.equals(platform)) {
            userId = naverUserRepository.findById(oAuth2UserInput.getId()).orElseGet(() -> registNaverUser(oAuth2UserInput)).getUserId();
        } else {
            throw new IllegalArgumentException("Unsupported platform");
        }
        log.info("user id: {}", userId);
        return new OAuth2CustomUser(userId, oAuth2UserInput.getAccessToken());

    }

    private KakaoUser registKakaoUser(OAuth2UserInput oAuth2UserInput) {
        return kakaoUserRepository.save(KakaoUser.createInstance(oAuth2UserInput.getId(), registUser(oAuth2UserInput).getId()));
    }

    private NaverUser registNaverUser(OAuth2UserInput oAuth2UserInput) {
        return naverUserRepository.save(NaverUser.createInstance(oAuth2UserInput.getId(), registUser(oAuth2UserInput).getId()));
    }

    private User registUser(OAuth2UserInput oAuth2UserInput) {
        User user = userRepository.save(User.createInstance(oAuth2UserInput.getPlatform()));
        userApiAdapter.registUser(user.getId(), oAuth2UserInput.getNickname());
        return user;
    }

    @Transactional
    public int deleteUser(OAuth2CustomUser user) {
        int id = Integer.parseInt(user.getName());
        OAuth2Platform platform = getPlatform(user);
        deletePlatformUserById(id, platform);
        userRepository.deleteById(id);
        return id;
    }

    private void deletePlatformUserById(int id, OAuth2Platform platform) {
        System.out.println(id);
        if(platform == OAuth2Platform.KAKAO){
            KakaoUser kakaoUser = kakaoUserRepository.findByUserId(id)
                    .orElseThrow(() -> new UserException(ErrorCode.NoUser));
            kakaoUserRepository.delete(kakaoUser);
        } else if(platform == OAuth2Platform.NAVER){
            NaverUser naverUser = naverUserRepository.findByUserId(id)
                    .orElseThrow(() -> new UserException(ErrorCode.NoUser));
            naverUserRepository.delete(naverUser);
        } else {
            throw new IllegalArgumentException("Unsupported platform");
        }
    }

    public OAuth2Platform getPlatform(OAuth2CustomUser user) {
        return userRepository.findById(Integer.parseInt(user.getName())).orElseThrow().getPlatform();
    }

    @Transactional
    public String updateAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshTokenId = patchRefreshToken(request);
        if (refreshTokenId == null) {
            throw new AuthException(ErrorCode.NeedLogin);
        }
        log.info(refreshTokenId);
        String oldKeyOrNewKey = refreshUtil.checkExpiredThenUpdateRefreshToken(response, refreshTokenId);
        return refreshUtil.reissue(oldKeyOrNewKey);
    }

    private String patchRefreshToken(HttpServletRequest request) {
        return CookieUtil.getCookie(request, "RefreshToken").map(Cookie::getValue).orElse(null);
    }

    @Transactional
    public Map<String, String> updateLoginTokenInfo(String name) {
        Map<String, String> tokens = new HashMap<>();
        String RefreshToken = refreshUtil.save(name);
        String AccessToken = refreshUtil.reissue(RefreshToken);
        tokens.put("RefreshToken", RefreshToken);
        tokens.put("AccessToken", AccessToken);
        return tokens;
    }

    public void deleteToken(HttpServletRequest request) {
        String refreshTokenId = patchRefreshToken(request);
        Assert.hasText(refreshTokenId, "refreshToken must not be empty");
        refreshUtil.deleteRefreshToken(refreshTokenId);
    }
}
