package org.dynabiz.spring.stepped.storage;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.dynabiz.workflow.stepped.SteppedTaskStateStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisTaskStorageConfig {

    @Bean
    @ConditionalOnMissingBean
    public SteppedTaskStateStorage steppedTaskStateStorage(StringRedisTemplate redisTemplate){
        return new SteppedTaskRedisStateStorage(redisTemplate, new ObjectMapper());
    }


}
