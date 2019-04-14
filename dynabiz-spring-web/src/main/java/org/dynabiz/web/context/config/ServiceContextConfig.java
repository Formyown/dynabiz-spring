package org.dynabiz.web.context.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.dynabiz.web.context.*;
import org.dynabiz.web.context.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

public class ServiceContextConfig {

    @Bean
    @ConditionalOnMissingBean
    public ServiceContextSerializer serviceContextSerializer(){
        ObjectMapper mapper = new ObjectMapper();
        return new StringServiceContextSerializer(mapper);
    }


    @Bean
    public ServletListenerRegistrationBean<?> registerCustomListener() {
        return new ServletListenerRegistrationBean<>(new ServiceContextLoaderListener());
    }

    @Bean
    public ServiceContextHolder serviceContextHolder(ServiceContextSerializer serializer,
                                                     ServiceContextStorage storage){
        return new ServiceContextHolder(serializer, storage);
    }
}
