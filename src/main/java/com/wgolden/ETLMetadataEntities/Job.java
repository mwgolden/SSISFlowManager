package com.wgolden.ETLMetadataEntities;

public class Job {
    public int jobId;
    public String jobName;
    public boolean enabled;
    public int ssisProjectId;
    public int externalJobId;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public int getExternalJobId() {
        return externalJobId;
    }

    public void setExternalJobId(int externalJobId) {
        this.externalJobId = externalJobId;
    }
}
