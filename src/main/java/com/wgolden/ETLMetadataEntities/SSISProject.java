package com.wgolden.ETLMetadataEntities;

public class SSISProject {
    public int ssisProjectId;
    public String ssisProjectName;
    public int ssisFolderId;
    public int applictationId;
    public boolean publishToSSISServer;
    public int externalSSISProjectId;

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public String getSsisProjectName() {
        return ssisProjectName;
    }

    public void setSsisProjectName(String ssisProjectName) {
        this.ssisProjectName = ssisProjectName;
    }

    public int getSsisFolderId() {
        return ssisFolderId;
    }

    public void setSsisFolderId(int ssisFolderId) {
        this.ssisFolderId = ssisFolderId;
    }

    public int getApplictationId() {
        return applictationId;
    }

    public void setApplictationId(int applictationId) {
        this.applictationId = applictationId;
    }

    public boolean isPublishToSSISServer() {
        return publishToSSISServer;
    }

    public void setPublishToSSISServer(boolean publishToSSISServer) {
        this.publishToSSISServer = publishToSSISServer;
    }

    public int getExternalSSISProjectId() {
        return externalSSISProjectId;
    }

    public void setExternalSSISProjectId(int externalSSISProjectId) {
        this.externalSSISProjectId = externalSSISProjectId;
    }
}
