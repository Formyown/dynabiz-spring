package io.dynabiz.workflow.stepped;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface StepKey {

    /**
     * KEY时间间隔
     * @return
     */
    long keyInterval() default 0;

    /**
     * KEY重试
     * @return
     */
    long keyRetry() default 0;


}
