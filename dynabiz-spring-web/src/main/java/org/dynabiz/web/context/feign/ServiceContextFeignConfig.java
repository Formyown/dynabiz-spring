package org.dynabiz.web.context.feign;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import feign.Request;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.dynabiz.BusinessException;
import org.dynabiz.web.chain.ServiceCallChainContext;
import org.dynabiz.web.chain.ServiceCallChainNode;
import org.dynabiz.web.context.ServiceContextHolder;
import org.dynabiz.web.response.GeneralResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Configuration
public class ServiceContextFeignConfig {

    @Bean
    @ConditionalOnMissingClass
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return requestTemplate -> {
            ServiceCallChainContext callChainContext = null;
            try{
                callChainContext = ServiceContextHolder.get(ServiceCallChainContext.class);
            }catch (IllegalStateException e){
                ServiceContextHolder.start();
            }

            if(callChainContext == null){
                callChainContext = new ServiceCallChainContext(new ArrayList<>());
                ServiceContextHolder.put(callChainContext);
            }

            ServiceCallChainNode node = new ServiceCallChainNode();
            node.setUrl(requestTemplate.url());
            callChainContext.getChain().add(node);

            ServiceContextHolder.flush(); //立即写入上下文
            requestTemplate.header("X-SERVICE-SESSION-ID", ServiceContextHolder.getSessionId());
        };
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(50000, 50000);
    }


    /**
     * feign数据解包
     * @param mapper
     * @return
     */
    @Bean
    public Decoder feignDecoder(ObjectMapper mapper) {
        return (response, type) -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(response.body().asInputStream(), outputStream);
            GeneralResponse gResp = mapper.readValue(new String(outputStream.toByteArray()), GeneralResponse.class);
            if(gResp.getCode() == 0)
                if(type instanceof Class<?>){
                    return mapper.convertValue(gResp.getData(), (Class<?>) type);
                }
                else{
                    return mapper.convertValue(gResp.getData(), TypeFactory.defaultInstance().constructType(type));
                }
            else throw new BusinessException(gResp.getCode(), gResp.getMsg());
        };
    }

    @Bean
    public ErrorDecoder feignErrorDecoder(ObjectMapper mapper){
        return (methodKey, response) -> {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GeneralResponse gResp = new GeneralResponse();
            try {
                IOUtils.copy(response.body().asInputStream(), outputStream);
                gResp = mapper.readValue(new String(outputStream.toByteArray()), GeneralResponse.class);
                if(gResp.getCode() == 0) throw new BusinessException(gResp.getCode(), gResp.getMsg());
            } catch (IOException e) {
                e.printStackTrace();
            }

            throw new BusinessException(gResp.getCode(), gResp.getMsg());
        };
    }


}
