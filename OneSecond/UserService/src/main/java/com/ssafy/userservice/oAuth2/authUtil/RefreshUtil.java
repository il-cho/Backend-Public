package com.ssafy.userservice.oAuth2.authUtil;

import com.ssafy.userservice.oAuth2.authrepository.RefreshRedisRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RefreshUtil {
    private final RefreshRedisRepository refreshRedisRepository;
    private final JwtUtil jwtUtil;

    public String save(String value) {
        String key = UUID.randomUUID().toString();
        refreshRedisRepository.save(key, value, 30, TimeUnit.DAYS);
        return key;
    }

    public String reissue(String key) {
        String value = findById(key);
        return createNewAccessToken(value);
    }

    public String checkExpiredThenUpdateRefreshToken(HttpServletResponse response, String key) {
        long exp = refreshRedisRepository.getExpire(key);
        if (exp <= TimeUnit.DAYS.toSeconds(7)) {
            String newKey = createNewRefreshToken(key);
            CookieUtil.addCookie(response, "RefreshToken", newKey, 2592000);
            return newKey;
        }
        return key;
    }

    public void deleteRefreshToken(String key) {
        refreshRedisRepository.delete(key);
    }

    private String findById(String key) {
        return (String) refreshRedisRepository.find(key);
    }

    private String createNewRefreshToken(String key) {
        String newKey = UUID.randomUUID().toString();
        String value = findById(key);
        refreshRedisRepository.save(newKey, value, 30, TimeUnit.DAYS);
        deleteRefreshToken(key);
        return newKey;
    }

    private String createNewAccessToken(String userId) {
        return jwtUtil.createToken(userId, 10800000);
    }

}
