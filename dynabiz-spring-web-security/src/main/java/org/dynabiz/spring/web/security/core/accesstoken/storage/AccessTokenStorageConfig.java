package org.dynabiz.spring.web.security.core.accesstoken.storage;

import org.dynabiz.spring.web.security.core.accesstoken.AccessTokenStorage;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;


@AutoConfigureAfter(RedisAutoConfiguration.class)
@ConditionalOnProperty(prefix = "access-token.storage", name = "name", havingValue = "redis", matchIfMissing = true)
public class AccessTokenStorageConfig {

    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    public AccessTokenStorage accessTokenStorage(StringRedisTemplate stringRedisTemplate){
        return new RedisAccessTokenStorage(stringRedisTemplate);
    }
}
