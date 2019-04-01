package io.dynabiz.web.error.config;


import io.dynabiz.web.error.ErrorHandlerController;
import io.dynabiz.web.response.config.EnableGeneralResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGeneralResponse
@Import({ErrorHandlerController.class})
@Documented
public @interface EnableGlobalErrorHandler {
}
