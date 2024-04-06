package com.wgolden.SSISDB.Manager;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.wgolden.SSISDB.Pojo.SSISEnvironment;
import com.wgolden.SSISDB.Pojo.SSISEnvironmentReference;
import com.wgolden.SSISDB.Pojo.SSISEnvironmentReferenceType;
import com.wgolden.SSISDB.Pojo.SSISEnvironmentVariable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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
    public void deleteEnvironmentVariable(SSISEnvironment ssisEnvironment, String variableName) throws RuntimeException{
        String deleteEnvVarStmt = """
                    catalog.delete_environment_variable
                         @folder_name  = ?
                        ,@environment_name = ?
                        ,@variable_name = ?
                """;
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(deleteEnvVarStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.setString(3, variableName);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public <T> SSISEnvironmentVariable<T> setEnvironmentVariable(SSISEnvironmentVariable<T> ssisEnvironmentVariable, T newValue) throws RuntimeException{
        String setEnvVarStmt = """
                    catalog.set_environment_variable_value
                        @folder_name = ?
                        ,@environment_name = ?
                        ,@variable_name = ?
                        ,@value = ?
                """;
        var ssisEnvironment = ssisEnvironmentVariable.getEnvironment();
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(setEnvVarStmt);
        ) {
            stmt.setString(1, ssisEnvironment.getFolderName());
            stmt.setString(2, ssisEnvironment.getEnvironmentName());
            stmt.setString(3, ssisEnvironmentVariable.getVariableName());
            stmt.setObject(4, newValue);
            stmt.execute();

            return new SSISEnvironmentVariable<>(
                    ssisEnvironment,
                    ssisEnvironmentVariable.getVariableName(),
                    ssisEnvironmentVariable.getDataType(),
                    ssisEnvironmentVariable.isSensitive(),
                    newValue,
                    ssisEnvironmentVariable.getDescription()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public long createEnvironmentReference(SSISEnvironment ssisEnvironment,
                                           String projectFolderName,
                                           String projectName,
                                           SSISEnvironmentReferenceType ssisEnvironmentReferenceType)
    throws RuntimeException {
        final String createEnvRefStmt = """
                    catalog.create_environment_reference
                        @folder_name = ?
                       ,@project_name = ?
                       ,@environment_name = ?
                       ,@reference_type = ?
                       ,@reference_id = ?
                """;
        long referenceId = -1;
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(createEnvRefStmt);){

            stmt.setString(1, projectFolderName);
            stmt.setString(2, projectName);
            stmt.setString(3, ssisEnvironment.getEnvironmentName());
            stmt.setString(4, ssisEnvironmentReferenceType.toString());
            stmt.registerOutParameter(5, Types.INTEGER);
            stmt.execute();
            referenceId = stmt.getInt(5);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return referenceId;
    }
    public <T> void setObjectParameterValue(SSISEnvironmentReference ssisEnvironmentReference,
                                            int objectType,
                                            String parameterName,
                                            T parameterValue){
        final String setObjParamStmt = """
                    catalog.set_object_parameter_value
                         @object_type     =  ?
                        ,@folder_name     =  ?
                        ,@project_name    =  ?
                        ,@parameter_name  =  ?
                        ,@parameter_value =  ?
                """;
        SSISEnvironment ssisEnvironment = ssisEnvironmentReference.getSsisEnvironment();
        try(Connection conn = ssisEnvironment.getDataSource().getConnection();
            CallableStatement stmt = conn.prepareCall(setObjParamStmt);
        ){
            stmt.setInt(1, objectType);
            stmt.setString(2, ssisEnvironment.getFolderName());
            stmt.setString(3, ssisEnvironmentReference.getProjectName());
            stmt.setString(4, parameterName);
            stmt.setObject(5, parameterValue);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
