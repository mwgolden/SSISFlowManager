package com.wgolden.ssisdb;

public class Project {

    public String projectName;
    public String folderName;
    public boolean use32BitRuntime;

    public Project(String projectName, String folderName, boolean use32BitRuntime) {
        this.projectName = projectName;
        this.folderName = folderName;
        this.use32BitRuntime = use32BitRuntime;
    }
}
