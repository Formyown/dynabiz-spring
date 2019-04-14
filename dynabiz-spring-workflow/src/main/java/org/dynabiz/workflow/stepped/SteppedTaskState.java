package org.dynabiz.workflow.stepped;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SteppedTaskState {
    @JsonProperty
    private int stepIndex;
    @JsonProperty
    private int retryNumber;
    @JsonProperty
    private int maxStepIndex;
    @JsonProperty
    private long lastInvoked;
    @JsonProperty
    private String data;

    public SteppedTaskState() {
    }

    public SteppedTaskState(int stepIndex, int retryNumber, int maxStepIndex, long lastInvoked, String data) {
        this.stepIndex = stepIndex;
        this.retryNumber = retryNumber;
        this.maxStepIndex = maxStepIndex;
        this.lastInvoked = lastInvoked;
        this.data = data;
    }

    public int getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public int getRetryNumber() {
        return retryNumber;
    }

    public void increaseRetryNumber(int n){
        this.retryNumber += n;
    }

    public void increaseStepIndex(int n){
        this.stepIndex += n;
    }

    public void setRetryNumber(int retryNumber) {
        this.retryNumber = retryNumber;
    }

    public int getMaxStepIndex() {
        return maxStepIndex;
    }

    public void setMaxStepIndex(int maxStepIndex) {
        this.maxStepIndex = maxStepIndex;
    }

    public long getLastInvoked() {
        return lastInvoked;
    }

    public void setLastInvoked(long lastInvoked) {
        this.lastInvoked = lastInvoked;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}