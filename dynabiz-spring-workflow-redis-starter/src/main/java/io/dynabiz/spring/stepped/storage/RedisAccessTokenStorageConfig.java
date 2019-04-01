package io.dynabiz.spring.stepped.storage;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.dynabiz.workflow.stepped.SteppedTaskStateStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisAccessTokenStorageConfig {

    @Bean
    @ConditionalOnMissingBean
    public SteppedTaskStateStorage accessTokenStorage(){
        return new SteppedTaskRedisStateStorage(new StringRedisTemplate(), new ObjectMapper());
    }


}
