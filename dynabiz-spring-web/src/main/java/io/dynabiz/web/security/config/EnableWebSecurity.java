package io.dynabiz.web.security.config;

import io.dynabiz.web.context.config.EnableServiceContext;
import io.dynabiz.web.security.core.accesstoken.AccessTokenConfig;
import io.dynabiz.web.security.core.accesstoken.AccessTokenConfigurationProperties;
import io.dynabiz.web.security.permission.PermissionVerificationAspect;
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
