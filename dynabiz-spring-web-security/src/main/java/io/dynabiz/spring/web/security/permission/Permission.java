package io.dynabiz.spring.web.security.permission;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Permission {
    String[] value() default {};
    String[] role() default {};
    String[] except() default {};
    boolean internal() default false;
}
