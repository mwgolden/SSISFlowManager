package com.wgolden.ETLMetadataEntities;

public class SSISEnvironment {
    public int ssisEnvironmentId;
    public String ssisEnvironmentName;
    public String ssisEnvironmentDescription;
    public int ssisFolderId;
    public int ssisProjectId;
    public int environmentTypeId;
    public int externalSSISEnvironmentId;

    public int getSsisEnvironmentId() {
        return ssisEnvironmentId;
    }

    public void setSsisEnvironmentId(int ssisEnvironmentId) {
        this.ssisEnvironmentId = ssisEnvironmentId;
    }

    public String getSsisEnvironmentName() {
        return ssisEnvironmentName;
    }

    public void setSsisEnvironmentName(String ssisEnvironmentName) {
        this.ssisEnvironmentName = ssisEnvironmentName;
    }

    public String getSsisEnvironmentDescription() {
        return ssisEnvironmentDescription;
    }

    public void setSsisEnvironmentDescription(String ssisEnvironmentDescription) {
        this.ssisEnvironmentDescription = ssisEnvironmentDescription;
    }

    public int getSsisFolderId() {
        return ssisFolderId;
    }

    public void setSsisFolderId(int ssisFolderId) {
        this.ssisFolderId = ssisFolderId;
    }

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public int getEnvironmentTypeId() {
        return environmentTypeId;
    }

    public void setEnvironmentTypeId(int environmentTypeId) {
        this.environmentTypeId = environmentTypeId;
    }

    public int getExternalSSISEnvironmentId() {
        return externalSSISEnvironmentId;
    }

    public void setExternalSSISEnvironmentId(int externalSSISEnvironmentId) {
        this.externalSSISEnvironmentId = externalSSISEnvironmentId;
    }
}
