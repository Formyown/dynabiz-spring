package org.dynabiz.web.error.config;


import org.dynabiz.web.error.ErrorHandlerController;
import org.dynabiz.web.response.config.EnableGeneralResponse;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGeneralResponse
@Import({ErrorHandlerController.class})
@Documented
public @interface EnableGlobalErrorHandler {
}
