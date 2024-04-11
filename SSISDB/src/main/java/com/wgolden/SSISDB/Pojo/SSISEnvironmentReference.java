package com.wgolden.SSISDB.Pojo;

public class SSISEnvironmentReference {
    private final SSISEnvironment ssisEnvironment;
    private final String projectFolderName;
    private final String projectName;
    private final SSISEnvironmentReferenceType ssisEnvironmentReferenceType;
    private final long environmentReferenceId;

    public SSISEnvironmentReference(SSISEnvironment ssisEnvironment,
                                    String projectFolderName,
                                    String projectName,
                                    SSISEnvironmentReferenceType ssisEnvironmentReferenceType,
                                    long environmentReferenceId) {
        this.ssisEnvironment = ssisEnvironment;
        this.projectFolderName = projectFolderName;
        this.projectName = projectName;
        this.ssisEnvironmentReferenceType = ssisEnvironmentReferenceType;
        this.environmentReferenceId = environmentReferenceId;
    }

    public SSISEnvironment getSsisEnvironment() {
        return ssisEnvironment;
    }

    public String getProjectFolderName() {
        return projectFolderName;
    }

    public String getProjectName() {
        return projectName;
    }

    public SSISEnvironmentReferenceType getSsisEnvironmentReferenceType() {
        return ssisEnvironmentReferenceType;
    }

    public long getEnvironmentReferenceId() {
        return environmentReferenceId;
    }
}
