package org.dynabiz.spring.web.security.core.accesstoken.storage;



import org.dynabiz.spring.web.security.core.accesstoken.AccessTokenManager;
import org.dynabiz.spring.web.security.core.accesstoken.AccessTokenStorage;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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
