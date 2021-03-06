package org.dynabiz.web.response.config;

import org.dynabiz.web.response.GeneralResponse;
import org.dynabiz.web.response.wrapper.ResponseBodyWrapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashSet;
import java.util.Set;


@Configuration
public class ResponseBodyWrapConfig implements ResponseBodyAdvice {

    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * 需要忽略的地址
     */
    private Set<String> ignores = new HashSet<String>(){
        {
            add( "/swagger-resources");
            add("/v2/api-docs");
        }
    };

    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (this.ignores.contains(serverHttpRequest.getURI().toString())) {
            return o;
        }
        if(o instanceof ResponseEntity){
            return o;
        }
        else {
            return new GeneralResponse(o);
        }
    }
}
