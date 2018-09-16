package io.dynabiz.web.context.config;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ServiceContextConfig.class})
@Documented
public @interface EnableServiceContext {
}
