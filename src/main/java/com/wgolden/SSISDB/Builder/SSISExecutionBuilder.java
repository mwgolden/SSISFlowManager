package com.wgolden.SSISDB.Builder;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.SSISExecution;

import java.sql.SQLException;
import java.util.HashMap;

public class SSISExecutionBuilder {

    private final HashMap<String, Object> parameters;
    private SSISExecutionManager ssisExecutionManager = null;
    private SQLServerDataSource dataSource;
    public SSISExecutionBuilder(){
        this.parameters = new HashMap<>();
    }

    public SSISExecutionBuilder packageName(String packageName){
        this.parameters.put("package_name", packageName);
        return this;
    }
    public SSISExecutionBuilder projectName(String projectName){
        this.parameters.put("project_name", projectName);
        return this;
    }
    public SSISExecutionBuilder folderName(String folderName){
        this.parameters.put("folder_name", folderName);
        return this;
    }
    public SSISExecutionBuilder dataSource(SQLServerDataSource dataSource){
        this.dataSource = dataSource;
        return this;
    }

    public SSISExecutionBuilder use32BitRuntime(boolean use32BitRuntime){
        this.parameters.put("use32BitRuntime", use32BitRuntime);
        return this;
    }
    public SSISExecutionBuilder ssisExecutionManager(SSISExecutionManager ssisExecutionManager) throws SQLException {
        this.ssisExecutionManager = ssisExecutionManager;
        return this;
    }
    public SSISExecutionBuilder environmentReferenceId(int environmentReferenceId) throws SQLException {
        this.parameters.put("reference_id", environmentReferenceId);
        return this;
    }
    public SSISExecutionBuilder runInScaleout(boolean runInScaleOut){
        this.parameters.put("runinscaleout", runInScaleOut);
        return this;
    }
    public SSISExecutionBuilder useAnyWorker(boolean useAnyWorker){
        this.parameters.put("useanyworker", useAnyWorker);
        return this;
    }
    public SSISExecution build() throws RuntimeException {
        checkError();
        long executionId = this.ssisExecutionManager.createExecution(this.parameters, this.dataSource);
        return new SSISExecution(
                (String) this.parameters.get("project_name"),
                (String) this.parameters.get("folder_name"),
                (String) this.parameters.get("package_name"),
                executionId,
                this.dataSource);
    }
    private void checkError(){
        StringBuilder sb = new StringBuilder();
        if(this.dataSource == null){
            sb.append("Data Source is null\n");
        }
        if(this.parameters.get("folder_name") == null){
            sb.append("Folder name is null\n");
        }
        if(this.parameters.get("project_name") == null){
            sb.append("Project name is null\n");
        }
        if(this.parameters.get("package_name") == null){
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
