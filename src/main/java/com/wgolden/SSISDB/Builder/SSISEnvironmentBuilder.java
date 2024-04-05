package com.wgolden.SSISDB.Builder;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Pojo.SSISEnvironment;

public class SSISEnvironmentBuilder {
    private  String folderName;
    private String environmentName;
    private String environmentDescription;
    private SQLServerDataSource dataSource = null;

    public SSISEnvironmentBuilder folderName(String folderName){
        this.folderName = folderName;
        return this;
    }
    public SSISEnvironmentBuilder environmentName(String environmentName){
        this.environmentName = environmentName;
        return this;
    }
    public SSISEnvironmentBuilder environmentDescription(String environmentDescription){
        this.folderName = environmentDescription;
        return this;
    }
    public SSISEnvironmentBuilder dataSource(SQLServerDataSource dataSource){
        this.dataSource = dataSource;
        return this;
    }
    public SSISEnvironment build(){
        return new SSISEnvironment(
                this.folderName,
                this.environmentName,
                this.environmentDescription,
                this.dataSource
        );
    }
}
