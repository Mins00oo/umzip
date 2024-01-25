package com.ssafy.umzip.global.util.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final String PREFIX = "phone:";

    private final RedisTemplate<String, String> redisTemplate;

    public void createSmsCertification(String phone, String certificationNumber) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        int LIMIT_TIME = 3 * 60;
        vop.set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getSmsCertification(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void removeSmsCertification(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + phone));
    }


    public void setValue(String key, String value, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

}
