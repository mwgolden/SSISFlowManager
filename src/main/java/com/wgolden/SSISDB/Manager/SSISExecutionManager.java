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

    public long createExecution(HashMap<String, Object> params, SQLServerDataSource dataSource) throws RuntimeException {
        long executionId = -1;
        StringBuilder sb = new StringBuilder();
        sb.append("EXECUTE [catalog].[create_execution]");
        for(String key : params.keySet()){
            sb.append(String.format("\n @%s = ?,", key));
        }
        sb.append("\n @execution_id = ?");
        String createExecutionStmt = sb.toString();
        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(createExecutionStmt);
        ) {
            int parameterIndex = 0;
            for(String key : params.keySet()){
                parameterIndex += 1;
                var param = params.get(key).getClass().getSimpleName();
                cstmt.setObject(parameterIndex, params.get(key));
            }
            parameterIndex += 1;
            cstmt.registerOutParameter(parameterIndex, Types.INTEGER);
            cstmt.execute();
            executionId = cstmt.getInt(parameterIndex);
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
