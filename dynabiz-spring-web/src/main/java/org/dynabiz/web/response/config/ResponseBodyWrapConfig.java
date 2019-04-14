package org.dynabiz.web.response.config;

import org.dynabiz.web.response.wrapper.ResponseBodyWrapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


public class ResponseBodyWrapConfig extends WebMvcConfigurationSupport {
    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        return super.requestMappingHandlerAdapter();
    }

    @Bean
    public ResponseBodyWrapFactoryBean getResponseBodyWrap() {
        return  new ResponseBodyWrapFactoryBean();
    }
}
