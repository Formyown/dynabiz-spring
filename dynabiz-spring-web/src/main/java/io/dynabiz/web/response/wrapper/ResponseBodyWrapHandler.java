package io.dynabiz.web.response.wrapper;


import io.dynabiz.web.response.GeneralResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {
    private final HandlerMethodReturnValueHandler delegate;
    public  ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate){
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        if(returnValue instanceof ResponseEntity){
            delegate.handleReturnValue(returnValue,returnType,mavContainer,webRequest);
        }
        else {
            GeneralResponse responseDto = new GeneralResponse(returnValue);
            delegate.handleReturnValue(responseDto,returnType,mavContainer,webRequest);
        }

    }
}
