package com.ssafy.userservice.oAuth2.authrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String key, Object value, long timeout, TimeUnit unit) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(key, value, timeout, unit);
    }

    public Object find(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

}
