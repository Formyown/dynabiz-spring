package io.dynabiz.spring.web.security.permission;


import io.dynabiz.spring.web.security.core.SecurityContext;
import io.dynabiz.std.exception.PermissionException;
import io.dynabiz.web.chain.ServiceCallChainContext;
import io.dynabiz.web.context.ServiceContextHolder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;


@Aspect
@Order
@Component
public class PermissionVerificationAspect {

    @Before("@annotation(io.dynabiz.spring.web.security.permission.Permission)")
    public void checkPermission(JoinPoint jp) {

        Permission permission = null;
        for (Annotation anno:((MethodSignature)(jp.getSignature())).getMethod().getAnnotations()) {
            if(!(anno instanceof Permission)){
                continue;
            }
           permission = (Permission) anno;
        }

        if(permission == null) return;
        //只允许内部调用
        if(permission.internal()){
            ServiceCallChainContext callChainContext = ServiceContextHolder.get(ServiceCallChainContext.class);
            if(callChainContext != null
                    && callChainContext.getChain() != null
                    && callChainContext.getChain().size() != 0){
                return;
            }
        }

        SecurityContext token = ServiceContextHolder.get(SecurityContext.class);
        if(token != null){
            String role = token.getRole();
            Set<String> permissions = token.getPermissions();
            //如果存在排除权限
            for (String i : permission.except()) {
                if(permissions.contains(i)) throw PermissionException.NO_PERMISSION;
            }
            //检测角色要求
            for (String reqRole : permission.role()) {
                if(role.equals(reqRole)) return;
            }
            //检查匹配权限
            if(permissions!= null && permissions.size() > 0
                    && permission.value().length > 0){
                for (String i:permission.value()) {
                    if(permissions.contains(i)) return;
                }
            }
        }

        throw PermissionException.NO_PERMISSION;

    }
}


