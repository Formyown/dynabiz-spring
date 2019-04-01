package io.dynabiz.workflow.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.dynabiz.web.context.ServiceContextStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

public class WorkflowConfig {


//    @Bean
//    @ConditionalOnMissingBean
//    VerificationCodeManager verificationCodeManager(ObjectMapper mapper, StringRedisTemplate stringRedisTemplate){
//        return new  VerificationCodeManager(stringRedisTemplate, mapper);
//    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceContextStorage serviceContextStorage(StringRedisTemplate stringRedisTemplate){
        return new RedisServiceContextStorage(stringRedisTemplate);
    }
}
