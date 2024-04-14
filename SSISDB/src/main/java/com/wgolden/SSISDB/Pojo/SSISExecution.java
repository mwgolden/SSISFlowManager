package com.wgolden.SSISDB.Pojo;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class SSISExecution {
    private final String folderName;
    private final String projectName;
    private final String packageName;
    private final long executionId;
    private final int retryCount;

    public SSISExecution(String projectName,
                         String folderName,
                         String packageName,
                         long executionId,
                         int retryCount){
        this.projectName = projectName;
        this.folderName = folderName;
        this.packageName = packageName;
        this.executionId = executionId;
        this.retryCount = retryCount;
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

    public int getRetryCount() {
        return retryCount;
    }
}
