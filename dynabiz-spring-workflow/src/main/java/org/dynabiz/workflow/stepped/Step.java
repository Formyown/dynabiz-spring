package org.dynabiz.workflow.stepped;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Step {
    int index();

    /**
     * 过期时间
     * @return
     */
    int ttl();

    /**
     * 重试次数，-1无限重试
     * @return
     */
    int retry() default -1;

    /**
     * 访问时间间隔
     * @return
     */
    long interval() default 0;

    boolean renewable() default false;


    StepKey stepKey() default @StepKey;

}
