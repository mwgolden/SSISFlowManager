package com.wgolden.ETLMetadataEntities;

public class SSISProjectEnvironment {
    public int ssisProjectEnvironmentId;
    public int ssisProjectId;
    public int ssisEnvironmentId;
    public boolean createReference;
    public boolean assignVariables;
    public int externalSSISProjectEnvironmentId;

    public int getSsisProjectEnvironmentId() {
        return ssisProjectEnvironmentId;
    }

    public void setSsisProjectEnvironmentId(int ssisProjectEnvironmentId) {
        this.ssisProjectEnvironmentId = ssisProjectEnvironmentId;
    }

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public int getSsisEnvironmentId() {
        return ssisEnvironmentId;
    }

    public void setSsisEnvironmentId(int ssisEnvironmentId) {
        this.ssisEnvironmentId = ssisEnvironmentId;
    }

    public boolean isCreateReference() {
        return createReference;
    }

    public void setCreateReference(boolean createReference) {
        this.createReference = createReference;
    }

    public boolean isAssignVariables() {
        return assignVariables;
    }

    public void setAssignVariables(boolean assignVariables) {
        this.assignVariables = assignVariables;
    }

    public int getExternalSSISProjectEnvironmentId() {
        return externalSSISProjectEnvironmentId;
    }

    public void setExternalSSISProjectEnvironmentId(int externalSSISProjectEnvironmentId) {
        this.externalSSISProjectEnvironmentId = externalSSISProjectEnvironmentId;
    }
}
