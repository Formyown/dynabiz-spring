package io.dynabiz.workflow.config;


import io.dynabiz.workflow.stepped.MemSteppedTaskStateStorage;
import io.dynabiz.workflow.stepped.SteppedTaskStateStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class WorkflowConfig {

    @ConditionalOnMissingBean
    @Bean
    SteppedTaskStateStorage steppedTaskStateStorage(){
        return new MemSteppedTaskStateStorage();
    }
}
