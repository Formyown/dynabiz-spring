package io.dynabiz.workflow.config;


import io.dynabiz.workflow.stepped.SteppedTaskExecutor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WorkflowConfig.class, SteppedTaskExecutor.class})
public @interface EnableWorkflowSupport {
}