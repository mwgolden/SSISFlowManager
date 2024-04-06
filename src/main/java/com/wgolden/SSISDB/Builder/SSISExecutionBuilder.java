package com.wgolden.SSISDB.Builder;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.SSISExecution;

import java.sql.SQLException;
import java.util.HashMap;

public class SSISExecutionBuilder {
    private String packageName;
    private String projectName;
    private String folderName;
    private SQLServerDataSource dataSource = null;
    private boolean use32BitRuntime = false;
    private long executionId;
    private int environmentReferenceId;

    private SSISExecutionManager ssisExecutionManager = null;

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
    public SSISExecutionBuilder dataSource(SQLServerDataSource dataSource){
        this.dataSource = dataSource;
        return this;
    }

    public SSISExecutionBuilder use32BitRuntime(boolean use32BitRuntime){
        this.use32BitRuntime = use32BitRuntime;
        return this;
    }
    public SSISExecutionBuilder ssisExecutionManager(SSISExecutionManager ssisExecutionManager) throws SQLException {
        this.ssisExecutionManager = ssisExecutionManager;
        return this;
    }
    public SSISExecutionBuilder environmentReferenceId(int environmentReferenceId) throws SQLException {
        this.environmentReferenceId = environmentReferenceId;
        return this;
    }
    public SSISExecution build() throws RuntimeException {
        checkError();
        var params = new HashMap<String, Object>();
        params.put("dataSource", this.dataSource);
        params.put("folderName", this.folderName);
        params.put("projectName", this.projectName);
        params.put("packageName", this.packageName);
        params.put("use32BitRuntime", this.use32BitRuntime);
        params.put("environmentReferenceId", this.environmentReferenceId);

        this.executionId = this.ssisExecutionManager.createExecution(params);
        return new SSISExecution(this.projectName, this.folderName,this.packageName, this.executionId, this.dataSource);
    }
    private void checkError(){
        StringBuilder sb = new StringBuilder();
        if(this.dataSource == null){
            sb.append("Data Source is null\n");
        }
        if(this.folderName == null){
            sb.append("Folder name is null\n");
        }
        if(this.projectName == null){
            sb.append("Project name is null\n");
        }
        if(this.packageName == null){
            sb.append("Package name is null\n");
        }
        if(this.ssisExecutionManager == null){
            sb.append("SSIS Execution Manager is null");
        }
        if(!sb.isEmpty()){
            sb.insert(0, "Error: One or more required fields are null\n");
            throw new RuntimeException(sb.toString());
        }
    }
}
