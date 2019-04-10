package io.dynabiz.spring.stepped.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dynabiz.workflow.stepped.SteppedTaskState;
import io.dynabiz.workflow.stepped.SteppedTaskStateStorage;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SteppedTaskRedisStateStorage implements SteppedTaskStateStorage {

    private final static String NS = "steppedTask:";
    private final static String LIMIT_NS = "steppedTask:limit:";
    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOps;
    private ObjectMapper mapper;

    public SteppedTaskRedisStateStorage(final StringRedisTemplate redisTemplate, final ObjectMapper mapper) {
        Objects.requireNonNull(redisTemplate, "RedisTemplate should not be null");
        Objects.requireNonNull(mapper, "RedisTemplate should not be null");
        this.redisTemplate = redisTemplate;
        this.valueOps = this.redisTemplate.opsForValue();
        this.mapper = new ObjectMapper();
    }

    @Override
    public boolean save(String token,  SteppedTaskState state, long ttl, boolean force) {
        String strData;
        try {
            strData = mapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(force){
            this.valueOps.set(NS + token, strData, ttl, TimeUnit.MILLISECONDS);
            return true;
        }else {
            Boolean b = this.valueOps.setIfAbsent(NS + token, strData);
            //avoid unboxing NPE warring
            return b == null ? false : b;
        }
    }

    @Override
    public SteppedTaskState find(String token) {
        try {
            return mapper.readValue(this.valueOps.get(token), SteppedTaskState.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(String token) {
        Boolean b = redisTemplate.delete(NS + token);
        return b == null ? false : b;
    }

    @Override
    public void updateExpire(String token, long ttl) {
        redisTemplate.expire(NS + token, ttl, TimeUnit.MILLISECONDS);
    }
}
