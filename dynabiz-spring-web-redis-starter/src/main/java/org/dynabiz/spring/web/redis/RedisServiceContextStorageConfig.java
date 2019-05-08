package org.dynabiz.spring.web.redis;


import org.dynabiz.web.context.ServiceContextStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
public class RedisServiceContextStorageConfig {

    @Bean
    @ConditionalOnMissingBean
    public ServiceContextStorage serviceContextStorage(StringRedisTemplate template){
        return new RedisServiceContextStorage(template);
    }

}
