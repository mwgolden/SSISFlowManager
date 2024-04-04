package com.wgolden.ssisdb;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.SQLException;
import java.util.ArrayList;

public class SSISExecutionBuilder {
    private String packageName;
    private String projectName;
    private String folderName;
    private ArrayList<ExecutionParameter> executionParameters;
    private SQLServerDataSource dataSource = null;
    private boolean use32BitRuntime = false;
    private long executionId;

    public SSISExecutionBuilder packageName(String packageName){
        this.packageName = packageName;
        return this;
    }
    public SSISExecutionBuilder projectName(String projectName){
        this.projectName = projectName;
        return this;
    }
    public SSISExecutionBuilder folderName(String folderName){
        this.folderName = folderName;
        return this;
    }
    public SSISExecutionBuilder addExecutionParameter(ExecutionParameter parameter){
        if(this.executionParameters == null){
            this.executionParameters = new ArrayList<ExecutionParameter>();
        }
        this.executionParameters.add(parameter);
        return  this;
    }

    public SSISExecutionBuilder dataSource(SQLServerDataSource dataSource){
        this.dataSource = dataSource;
        return this;
    }

    public SSISExecutionBuilder use32BitRuntime(boolean use32BitRuntime){
        this.use32BitRuntime = use32BitRuntime;
        return this;
    }
    public SSISExecutionBuilder createExecution(SSISDBManager ssisdbManager) throws SQLException{
        this.executionId = ssisdbManager.createExecution(
                this.dataSource,
                this.folderName,
                this.projectName,
                this.packageName,
                this.use32BitRuntime
            );
        return this;
    }
    public SSISExecution build(){
        return new SSISExecution(this.projectName, this.folderName,this.packageName, this.executionId, this.dataSource);
    }
}
