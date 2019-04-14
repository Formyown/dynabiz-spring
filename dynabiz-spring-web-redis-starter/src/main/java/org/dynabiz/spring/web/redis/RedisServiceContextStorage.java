package org.dynabiz.spring.web.redis;

import org.dynabiz.web.context.ServiceContextStorage;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisServiceContextStorage implements ServiceContextStorage<String> {
    private StringRedisTemplate template;
    private static final String NS = "service-context:";
    protected static final long TTL = 1000 * 60 * 5;
    public RedisServiceContextStorage(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void saveAll(String sessionId, Map<String, String> dataMap) {
        HashOperations<String, String, String> operations = template.opsForHash();
        operations.putAll(NS + sessionId, dataMap);
        template.expire(NS + sessionId, TTL, TimeUnit.MILLISECONDS);
    }

    @Override
    public void save(String sessionId, String className, String data) {
        HashOperations<String, String, String> operations = template.opsForHash();
        operations.put(NS + sessionId, className, data);
        template.expire(NS + sessionId, TTL, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean exists(String sessionId) {
        return template.hasKey(NS + sessionId);
    }

    @Override
    public boolean exists(String sessionId, String className) {
        HashOperations<String, String, String> operations = template.opsForHash();
        return operations.hasKey(NS + sessionId, className);
    }

    @Override
    public void removeAll(String sessionId) {
        template.delete(sessionId);
    }

    @Override
    public void remove(String sessionId, String className) {
        HashOperations<String, String, String> operations = template.opsForHash();
        operations.delete(NS + sessionId, className);
    }

    @Override
    public String read(String sessionId, String className) {
        HashOperations<String, String, String> operations = template.opsForHash();
        return operations.get(NS + sessionId, className);
    }

    @Override
    public Map<String, String> readAll(String sessionId) {
        HashOperations<String, String, String> operations = template.opsForHash();
        return operations.entries(NS + sessionId);
    }
}
