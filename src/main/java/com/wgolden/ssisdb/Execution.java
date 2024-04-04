package com.wgolden.ssisdb;

public class Execution {
    public Project project;
    public String packageName;
    public long executionId;

    public Execution(Project project, String packageName, long executionId){
        this.project = project;
        this.packageName = packageName;
        this.executionId = executionId;
    }
}
