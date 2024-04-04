package com.wgolden.ssisdb;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class SSISDBManager {

    private static SSISDBManager instance;

    private SSISDBManager(){

    }

    public static synchronized SSISDBManager getInstance(){
        if(instance == null){
            instance = new SSISDBManager();
        }
        return instance;
    }
    public Execution createExecution(SQLServerDataSource dataSource, Project project, String packageName) throws SQLException {
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
            cstmt.setString(1, project.folderName);
            cstmt.setString(2, project.projectName);
            cstmt.setString(3, packageName);
            cstmt.setBoolean(4, project.use32BitRuntime);
            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.execute();
            executionId = cstmt.getInt(5);
            return new Execution(
                    project,
                    packageName,
                    executionId
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean startExecution(SQLServerDataSource dataSource, Execution execution){
        final String startExecutionStmt = """
                    EXECUTE [catalog].[start_execution]
                         @execution_id = ?
                        ,@retry_count = 0
                """;
        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(startExecutionStmt);
        ) {
            cstmt.setLong(1, execution.executionId);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
