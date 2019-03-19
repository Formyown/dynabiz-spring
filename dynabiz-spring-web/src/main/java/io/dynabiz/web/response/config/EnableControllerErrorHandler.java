package io.dynabiz.web.response.config;


import io.dynabiz.web.error.ErrorHandlerController;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGeneralResponse
@Import({ErrorHandlerController.class})
@Documented
public @interface EnableControllerErrorHandler {
}
