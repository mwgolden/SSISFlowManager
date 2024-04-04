package com.wgolden.ssisdb;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class SSISExecution {
    private String folderName;
    private String projectName;
    private String packageName;
    private long executionId;

    private SQLServerDataSource dataSource;

    public SSISExecution(String projectName,
                         String folderName,
                         String packageName,
                         long executionId,
                         SQLServerDataSource dataSource){
        this.projectName = projectName;
        this.folderName = folderName;
        this.packageName = packageName;
        this.executionId = executionId;
        this.dataSource = dataSource;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public long getExecutionId() {
        return executionId;
    }

    public SQLServerDataSource getDataSource() {
        return dataSource;
    }
}
