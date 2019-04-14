package org.dynabiz.workflow.stepped;

import java.lang.reflect.Method;


public class StepMethodData{
    private Step stepMetaData;
    private Method method;

    public StepMethodData() {
    }

    public StepMethodData(Step stepMetaData, Method method) {
        this.stepMetaData = stepMetaData;
        this.method = method;
    }

    public Step getStepMetaData() {
        return stepMetaData;
    }

    public void setStepMetaData(Step stepMetaData) {
        this.stepMetaData = stepMetaData;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}