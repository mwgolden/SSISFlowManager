package com.wgolden.ssisdb;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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

    public long createExecution(SQLServerDataSource dataSource,
                                         String folderName,
                                         String projectName,
                                         String packageName,
                                         boolean use32BitRuntime
    ) throws RuntimeException {
        long executionId = -1;
        String createExecutionStmt = """
                    EXECUTE [catalog].[create_execution]
                       @folder_name = ?
                      ,@project_name = ?
                      ,@package_name = ?
                      ,@use32bitruntime=?
                      ,@execution_id= ?
                """;
        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(createExecutionStmt);
        ) {
            cstmt.setString(1, folderName);
            cstmt.setString(2, projectName);
            cstmt.setString(3, packageName);
            cstmt.setBoolean(4, use32BitRuntime);
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
}