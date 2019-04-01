package io.dynabiz.spring.web.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dynabiz.spring.web.security.core.accesstoken.AccessToken;
import io.dynabiz.spring.web.security.core.accesstoken.AccessTokenStorage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisAccessTokenStorage implements AccessTokenStorage {

    private static final String ACCESS_NS = "token:access:";
    private static final String REFRESH_NS = "token:refresh:";
    private StringRedisTemplate redisTemplate;
    private ObjectMapper mapper;

    public RedisAccessTokenStorage(final StringRedisTemplate redisTemplate) {
        Objects.requireNonNull(redisTemplate, "RedisTemplate should not be null");
        Objects.requireNonNull(mapper, "RedisTemplate should not be null");
        this.redisTemplate = redisTemplate;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void save(final AccessToken token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String json;
        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ops.set(ACCESS_NS + token.getAccessToken(), json);
        ops.set(REFRESH_NS + token.getRefreshToken(), json);
        redisTemplate.expire(ACCESS_NS + token.getAccessToken(),
                token.getAccessTokenExpireIn() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        redisTemplate.expire(REFRESH_NS + token.getRefreshToken(),
                token.getRefreshTokenExpireIn() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public AccessToken findByAccessTokenValue(final String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if(!redisTemplate.hasKey(ACCESS_NS + token)) return null;
        String json = ops.get(ACCESS_NS + token);
        try {
            return mapper.readValue(json, AccessToken.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AccessToken findByRefreshTokenValue(final String token) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if(!redisTemplate.hasKey(REFRESH_NS + token)) return null;
        String json = ops.get(REFRESH_NS + token);
        try {
            return mapper.readValue(json, AccessToken.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void removeByAccessTokenValue(String token) {
        redisTemplate.delete(ACCESS_NS + token);
    }

    @Override
    public void removeByRefreshTokenValue(String token) {
        redisTemplate.delete(REFRESH_NS + token);
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean existsAccessTokenValue(String token) {
        return redisTemplate.hasKey(ACCESS_NS + token);
    }

    @Override
    public boolean existsRefreshTokenValue(String token) {
        return redisTemplate.hasKey(REFRESH_NS + token);
    }

}
