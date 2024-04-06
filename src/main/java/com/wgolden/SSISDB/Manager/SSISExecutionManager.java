package com.wgolden.SSISDB.Manager;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Pojo.SSISExecution;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

public class SSISExecutionManager {

    private static SSISExecutionManager instance;

    private SSISExecutionManager(){

    }

    public static synchronized SSISExecutionManager getInstance(){
        if(instance == null){
            instance = new SSISExecutionManager();
        }
        return instance;
    }

    public long createExecution(HashMap<String, Object> params) throws RuntimeException {
        long executionId = -1;
        String createExecutionStmt = """
                    EXECUTE [catalog].[create_execution]
                       @folder_name = ?
                      ,@project_name = ?
                      ,@package_name = ?
                      ,@use32bitruntime=?
                      ,@execution_id= ?
                """;
        if(params.get("environmentReferenceId") != null){
            createExecutionStmt += "\n @reference_id = ?";
        }
        var dataSource = (SQLServerDataSource)params.get("dataSource");
        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(createExecutionStmt);
        ) {
            cstmt.setString(1, (String)params.get("folderName"));
            cstmt.setString(2, (String)params.get("projectName"));
            cstmt.setString(3, (String)params.get("packageName"));
            cstmt.setBoolean(4, (boolean)params.get("use32BitRuntime"));
            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.execute();
            executionId = cstmt.getInt(5);
            return executionId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void startExecution(SSISExecution ssisExecution) throws RuntimeException{
        final String startExecutionStmt = """
                    EXECUTE [catalog].[start_execution]
                         @execution_id = ?
                        ,@retry_count = 0
                """;
        try(Connection conn = ssisExecution.getDataSource().getConnection();
            CallableStatement cstmt = conn.prepareCall(startExecutionStmt);
        ) {
            cstmt.setLong(1, ssisExecution.getExecutionId());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createExecutionDump(SSISExecution ssisExecution) throws RuntimeException{
        final String createExecutionDmpStmt = """
                    EXECUTE [catalog].[create_execution_dump]
                         @execution_id = ?
                """;

        try(Connection conn = ssisExecution.getDataSource().getConnection();
            CallableStatement cstmt = conn.prepareCall(createExecutionDmpStmt);
        ) {
            cstmt.setLong(1, ssisExecution.getExecutionId());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stopExecution(SSISExecution ssisExecution) throws RuntimeException{
        final String createExecutionDmpStmt = """
                    EXECUTE [catalog].[stop_operation]
                         @operation_id = ?
                """;

        try(Connection conn = ssisExecution.getDataSource().getConnection();
            CallableStatement cstmt = conn.prepareCall(createExecutionDmpStmt);
        ) {
            cstmt.setLong(1, ssisExecution.getExecutionId());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setExecutionParameter(SSISExecutionParameter ssisExecutionParameter){
        final String setExecutionParameterStmt = """
                    EXECUTE [catalog].[set_execution_parameter_value]
                         @execution_id = ?
                        ,@object_type = ?
                        ,@parameter_name = ?
                        ,@parameter_value = ?
                """;
        try(Connection conn = ssisExecutionParameter.getDataSource().getConnection();
            CallableStatement cstmt = conn.prepareCall(setExecutionParameterStmt);
        ) {
            cstmt.setLong(1, ssisExecutionParameter.getExecutionId());
            cstmt.setInt(2, ssisExecutionParameter.getObjectType());
            cstmt.setString(3, ssisExecutionParameter.getParameterName());
            cstmt.setObject(4, ssisExecutionParameter.getParameterValue());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
