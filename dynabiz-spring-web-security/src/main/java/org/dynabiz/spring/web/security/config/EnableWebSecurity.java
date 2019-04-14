package org.dynabiz.spring.web.security.config;

import org.dynabiz.spring.web.security.core.accesstoken.AccessTokenConfig;
import org.dynabiz.spring.web.security.core.accesstoken.AccessTokenConfigurationProperties;
import org.dynabiz.spring.web.security.permission.PermissionVerificationAspect;
import org.dynabiz.web.context.config.EnableServiceContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({AccessTokenConfig.class, PermissionVerificationAspect.class})
@EnableServiceContext
@EnableAspectJAutoProxy
@EnableConfigurationProperties(AccessTokenConfigurationProperties.class)
@Documented
public @interface EnableWebSecurity {
}
