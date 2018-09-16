package io.dynabiz.web.response.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ResponseBodyWrapConfig.class})
@Documented
public @interface EnableGeneralResponse {
}
