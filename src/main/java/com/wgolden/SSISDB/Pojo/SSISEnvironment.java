package com.wgolden.SSISDB.Pojo;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class SSISEnvironment {
    private final  String folderName;
    private final String environmentName;
    private final String environmentDescription;
    private final SQLServerDataSource dataSource;

    public SSISEnvironment(String folderName, String environmentName, SQLServerDataSource dataSource) {
        this.folderName = folderName;
        this.environmentName = environmentName;
        this.environmentDescription = null;
        this.dataSource = dataSource;
    }
    public SSISEnvironment(String folderName, String environmentName, String environmentDescription, SQLServerDataSource dataSource) {
        this.folderName = folderName;
        this.environmentName = environmentName;
        this.environmentDescription = environmentDescription;
        this.dataSource = dataSource;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public String getEnvironmentDescription() {
        return environmentDescription;
    }

    public SQLServerDataSource getDataSource() {
        return dataSource;
    }
}
