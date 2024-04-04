package com.wgolden.ETLMetadataEntities;

public class JobStep {
    public int jobStepId;
    public String jobStepName;
    public int jobId;
    public int executionOrder;
    public int externalJobStepId;

    public int getJobStepId() {
        return jobStepId;
    }

    public void setJobStepId(int jobStepId) {
        this.jobStepId = jobStepId;
    }

    public String getJobStepName() {
        return jobStepName;
    }

    public void setJobStepName(String jobStepName) {
        this.jobStepName = jobStepName;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(int executionOrder) {
        this.executionOrder = executionOrder;
    }

    public int getExternalJobStepId() {
        return externalJobStepId;
    }

    public void setExternalJobStepId(int externalJobStepId) {
        this.externalJobStepId = externalJobStepId;
    }
}
