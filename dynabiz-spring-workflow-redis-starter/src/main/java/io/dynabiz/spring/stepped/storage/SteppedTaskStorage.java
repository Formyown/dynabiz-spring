package io.dynabiz.spring.stepped.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import io.dynabiz.spring.web.security.core.accesstoken.AccessToken;
import io.dynabiz.spring.web.security.core.accesstoken.AccessTokenStorage;
import io.dynabiz.workflow.stepped.SteppedTaskStateStorage;
import io.dynabiz.workflow.stepped.TaskState;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SteppedTaskRedisStateStorage implements SteppedTaskStateStorage {

    private final static String NS = "steppedTask:";
    private final static String LIMIT_NS = "steppedTask:limit:";
    private final StringRedisTemplate redisTemplate;
    private ObjectMapper mapper;

    public SteppedTaskRedisStateStorage(final StringRedisTemplate redisTemplate, final ObjectMapper mapper) {
        Objects.requireNonNull(redisTemplate, "RedisTemplate should not be null");
        Objects.requireNonNull(mapper, "RedisTemplate should not be null");
        this.redisTemplate = redisTemplate;
        this.mapper = new ObjectMapper();
    }


    @Override
    public boolean save(String token, @NotNull TaskState state, long ttl, boolean force) {

        if(force){

        }else {
            try {
                return .setIfAbsent(NS + token, mapper.writeValueAsString(state));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public TaskState find(String token) {
        return null;
    }

    @Override
    public void remove(String token) {

    }

    @Override
    public void updateExpire(String token, long ttl) {

    }
}
