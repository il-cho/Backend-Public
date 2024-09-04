package com.ssafy.userservice.oAuth2.authEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = "refresh_token", timeToLive = 2592000)
public class RefreshToken {

    @Id
    String id;
    int userId;
    @TimeToLive
    long expires;

    public static RefreshToken createInstance(int userId) {
        return RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .build();
    }
}
