package com.wgolden.SSISDB.Builder;

import com.wgolden.SSISDB.Manager.SSISEnvironmentManager;
import com.wgolden.SSISDB.Pojo.SSISEnvironment;
import com.wgolden.SSISDB.Pojo.SSISEnvironmentReference;
import com.wgolden.SSISDB.Pojo.SSISEnvironmentReferenceType;

public class SSISEnvironmentReferenceBuilder {
    private SSISEnvironment ssisEnvironment;
    private String projectFolderName;
    private String projectName;
    private SSISEnvironmentReferenceType ssisEnvironmentReferenceType;
    private SSISEnvironmentManager ssisEnvironmentManager;

    public SSISEnvironmentReferenceBuilder ssisEnvironment(SSISEnvironment ssisEnvironment){
        this.ssisEnvironment = ssisEnvironment;
        return this;
    }
    public SSISEnvironmentReferenceBuilder projectFolderName(String projectFolderName){
        this.projectFolderName = projectFolderName;
        return this;
    }
    public SSISEnvironmentReferenceBuilder projectName(String projectName){
        this.projectName = projectName;
        return this;
    }
    public SSISEnvironmentReferenceBuilder ssisEnvironmentReferenceType(SSISEnvironmentReferenceType ssisEnvironmentReferenceType){
        this.ssisEnvironmentReferenceType = ssisEnvironmentReferenceType;
        return this;
    }
    public SSISEnvironmentReferenceBuilder ssisEnvironmentManager(SSISEnvironmentManager ssisEnvironmentManager){
        this.ssisEnvironmentManager = ssisEnvironmentManager;
        return this;
    }
    public SSISEnvironmentReference build(){
        long environmentReferenceId = this.ssisEnvironmentManager.createEnvironmentReference(
                this.ssisEnvironment,
                this.projectFolderName,
                this.projectName,
                this.ssisEnvironmentReferenceType
        );
        return new SSISEnvironmentReference(
                this.ssisEnvironment,
                this.projectFolderName,
                this.projectName,
                this.ssisEnvironmentReferenceType,
                environmentReferenceId
        );
    }
}
