package io.dynabiz.spring.web.redis;


import io.dynabiz.web.context.ServiceContextStorage;
import io.dynabiz.web.security.core.accesstoken.AccessTokenStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
public class RedisServiceContextStorageConfig {

    @Bean
    @ConditionalOnMissingBean
    public ServiceContextStorage serviceContextStorage(){
        return new RedisServiceContextStorage(new StringRedisTemplate());
    }

}
