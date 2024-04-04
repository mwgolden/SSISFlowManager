package com.wgolden.ETLMetadataEntities;

public class JobStepPackage {
    public int jobStepId;
    public int packageId;
    public int channelId;
    public boolean eventLoggingEnabled;
    public int intParameter;
    public String varcharParameter;
    public int externalJobStepId;
    public int externalPackageId;

    public int getJobStepId() {
        return jobStepId;
    }

    public void setJobStepId(int jobStepId) {
        this.jobStepId = jobStepId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public boolean isEventLoggingEnabled() {
        return eventLoggingEnabled;
    }

    public void setEventLoggingEnabled(boolean eventLoggingEnabled) {
        this.eventLoggingEnabled = eventLoggingEnabled;
    }

    public int getIntParameter() {
        return intParameter;
    }

    public void setIntParameter(int intParameter) {
        this.intParameter = intParameter;
    }

    public String getVarcharParameter() {
        return varcharParameter;
    }

    public void setVarcharParameter(String varcharParameter) {
        this.varcharParameter = varcharParameter;
    }

    public int getExternalJobStepId() {
        return externalJobStepId;
    }

    public void setExternalJobStepId(int externalJobStepId) {
        this.externalJobStepId = externalJobStepId;
    }

    public int getExternalPackageId() {
        return externalPackageId;
    }

    public void setExternalPackageId(int externalPackageId) {
        this.externalPackageId = externalPackageId;
    }
}
