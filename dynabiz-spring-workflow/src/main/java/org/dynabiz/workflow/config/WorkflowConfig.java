package org.dynabiz.workflow.config;


import org.dynabiz.workflow.stepped.MemSteppedTaskStateStorage;
import org.dynabiz.workflow.stepped.SteppedTaskStateStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class WorkflowConfig {

    @ConditionalOnMissingBean
    @Bean
    SteppedTaskStateStorage steppedTaskStateStorage(){
        return new MemSteppedTaskStateStorage();
    }
}
