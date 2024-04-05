package com.wgolden.SSISDB.Manager;

import com.wgolden.SSISDB.Pojo.SSISEnvironment;
import com.wgolden.SSISDB.Pojo.SSISEnvironmentVariable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SSISEnvironmentManager {
    private static SSISEnvironmentManager instance;
    private SSISEnvironmentManager(){}
    public static SSISEnvironmentManager getInstance(){
        if(instance == null){
            instance = new SSISEnvironmentManager();
        }
        return instance;
    }
    public void createEnvironment(SSISEnvironment ssisEnvironment) throws RuntimeException{

        String createEnvStmt = """
                    EXECUTE catalog.create_environment
                         @folder_name = ?
                        ,@environment_name = ?
                        ,@environment_description = ?
                """;
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(createEnvStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.setString(3, ssisEnvironment.getEnvironmentDescription());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteEnvironment(SSISEnvironment ssisEnvironment) throws RuntimeException{

        String createEnvStmt = """
                    EXECUTE catalog.delete_environment
                         @folder_name = ?
                        ,@environment_name = ?
                """;
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(createEnvStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public SSISEnvironment moveEnvironment(SSISEnvironment ssisEnvironment, String destinationFolder) throws RuntimeException{

        String moveEnvStmt = """
                    EXECUTE catalog.move_environment
                         @source_folder = ?
                        ,@environment_name = ?
                        ,@destination_folder = ?
                """;
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(moveEnvStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.setString(3, destinationFolder);
            stmt.execute();
            return new SSISEnvironment(
                    destinationFolder,
                    ssisEnvironment.getEnvironmentName(),
                    ssisEnvironment.getDataSource()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public SSISEnvironment renameEnvironment(SSISEnvironment ssisEnvironment, String newEnvironmentName) throws RuntimeException{

        String moveEnvStmt = """
                    EXECUTE catalog.rename_environment
                         @folder_name = ?
                        ,@environment_name = ?
                        ,@new_environment_name = ?
                """;
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(moveEnvStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.setString(3, newEnvironmentName);
            stmt.execute();
            return new SSISEnvironment(
                    ssisEnvironment.getFolderName(),
                    newEnvironmentName,
                    ssisEnvironment.getDataSource()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void createEnvironmentVariable(SSISEnvironmentVariable ssisEnvironmentVariable) throws RuntimeException{
        String createEnvVarStmt = """
                    catalog.create_environment_variable
                          @folder_name =  ?
                        , @environment_name =  ?
                        , @variable_name =  ?
                        , @data_type =  ?
                        , @sensitive =  ?
                        , @value =  ?
                        , @description =  ?
                """;
        var ssisEnvironment = ssisEnvironmentVariable.getEnvironment();
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(createEnvVarStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.setString(3, ssisEnvironmentVariable.getVariableName());
            stmt.setString(4, ssisEnvironmentVariable.getDataType().toString());
            stmt.setBoolean(5, ssisEnvironmentVariable.isSensitive());
            stmt.setObject(6, ssisEnvironmentVariable.getValue());
            stmt.setString(7, ssisEnvironmentVariable.getDescription());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
