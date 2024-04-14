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
    /**
     * Returns the singleton instance of the SSISExecutionManager.
     * <p>
     * This method ensures that only one instance of SSISExecutionManager is created
     * and returned throughout the application's lifecycle. It follows the
     * Singleton design pattern to provide a global point of access to the
     * SSISExecutionManager instance.
     * <p>
     * The instance is lazily initialized, meaning it is created only when
     * {@code getInstance()} is called for the first time.
     * <p>
     * Note: This method is thread-safe.
     *
     * @return The singleton instance of the SSISExecutionManager.
     */
    public static synchronized SSISExecutionManager getInstance(){
        if(instance == null){
            instance = new SSISExecutionManager();
        }
        return instance;
    }

    /**
     * Creates an instance of execution in the Integration Services catalog.
     *
     * @param params    The parameters for the catalog.create_execution stored procedure
     * @param dataSource    The SQL Server data source
     * @return  The Id of the created execution
     * @throws SQLException is a sql error occurs
     */
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

    /**
     * Starts an instance of execution in the Integration Services catalog.
     *
     * @param ssisExecution An instance in the SSISExecution class
     * @throws SQLException if a sql error occurs
     */
    public void startExecution(SSISExecution ssisExecution, SQLServerDataSource dataSource) throws RuntimeException{
        final String startExecutionStmt = """
                    EXECUTE [catalog].[start_execution]
                         @execution_id = ?
                        ,@retry_count = ?
                """;
        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(startExecutionStmt);
        ) {
            cstmt.setLong(1, ssisExecution.getExecutionId());
            cstmt.setInt(2, ssisExecution.getRetryCount());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void startExecution(long executionId, int retryCount, SQLServerDataSource dataSource) throws RuntimeException{
        final String startExecutionStmt = """
                    EXECUTE [catalog].[start_execution]
                         @execution_id = ?
                        ,@retry_count = ?
                """;
        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(startExecutionStmt);
        ) {
            cstmt.setLong(1, executionId);
            cstmt.setInt(2, retryCount);
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Causes a running package to pause and create a dump file. The file is stored in the <drive>:\Program Files\Microsoft SQL Server\130\Shared\ErrorDumps folder.
     *
     * @param ssisExecution An instance in the SSISExecution class
     * @throws SQLException if a SQL error occurs
     */
    public void createExecutionDump(SSISExecution ssisExecution, SQLServerDataSource dataSource) throws RuntimeException{
        final String createExecutionDmpStmt = """
                    EXECUTE [catalog].[create_execution_dump]
                         @execution_id = ?
                """;

        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(createExecutionDmpStmt);
        ) {
            cstmt.setLong(1, ssisExecution.getExecutionId());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops an instance of execution in the Integration Services catalog.
     *
     * @param ssisExecution An instance in the SSISExecution class
     * @throws SQLException if a SQL error occurs
     */
    public void stopExecution(SSISExecution ssisExecution, SQLServerDataSource dataSource) throws RuntimeException{
        final String stopExecutionDmpStmt = """
                    EXECUTE [catalog].[stop_operation]
                         @operation_id = ?
                """;

        try(Connection conn = dataSource.getConnection();
            CallableStatement cstmt = conn.prepareCall(stopExecutionDmpStmt);
        ) {
            cstmt.setLong(1, ssisExecution.getExecutionId());
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the value of a parameter for an instance of execution in the Integration Services catalog.
     *
     * @param ssisExecutionParameter    An instance of the SSISExecutionParameter Class
     * @throws SQLException if a SQL error occurs
     */
    public void setExecutionParameter(SSISExecutionParameter ssisExecutionParameter, SQLServerDataSource dataSource){
        final String setExecutionParameterStmt = """
                    EXECUTE [catalog].[set_execution_parameter_value]
                         @execution_id = ?
                        ,@object_type = ?
                        ,@parameter_name = ?
                        ,@parameter_value = ?
                """;
        try(Connection conn = dataSource.getConnection();
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
