package io.dynabiz.web.context.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.dynabiz.web.context.ServiceContextLoaderListener;
import io.dynabiz.web.context.ServiceContextSerializer;
import io.dynabiz.web.context.StringServiceContextSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;


public class ServiceContextConfig {

    @Bean
    @ConditionalOnMissingBean
    public ServiceContextSerializer serviceContextSerializer(ObjectMapper mapper){
        return new StringServiceContextSerializer(mapper);
    }


    @Bean
    public ServletListenerRegistrationBean<?> registerCustomListener() {
        return new ServletListenerRegistrationBean<>(new ServiceContextLoaderListener());
    }

}
