package com.wgolden;

import com.microsoft.sqlserver.jdbc.*;
import com.wgolden.ssisdb.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var props = new Properties();
        try {
            props.load(Files.newInputStream(
                    Path.of("project.properties"),
                    StandardOpenOption.READ
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var datasource = new SQLServerDataSource();
        datasource.setServerName(props.getProperty("ssisdb.server"));
        datasource.setPortNumber(Integer.parseInt(props.getProperty("ssisdb.port")));
        datasource.setDatabaseName(props.getProperty("ssisdb.database"));
        datasource.setUser(System.getenv("MSSQLUSER"));
        datasource.setPassword(System.getenv("MSSQLPASS"));
        datasource.setIntegratedSecurity(true);
        datasource.setTrustServerCertificate(true);
        datasource.setAuthenticationScheme("NTLM");

        SSISEnvironmentManager environmentManager = SSISEnvironmentManager.getInstance();
        try{
            String folderName = "test_folder";
            // environment already exists, so we don't need to create it
            SSISEnvironment ssisEnvironment = new SSISEnvironmentBuilder()
                    .environmentName("my_test_environment")
                    .folderName(folderName)
                    .dataSource(datasource)
                    .build();

            SSISEnvironmentVariable<String> ssisEnvironmentVariable = new SSISEnvironmentVariableBuilder<String>()
                    .ssisEnvironment(ssisEnvironment)
                    .variableName("my_env_variable")
                    .variableDataType(SSISEnvironmentVariableDatatype.String)
                    .variableValue("Oh look a string variable")
                    .isSensitive(false)
                    .build();

            environmentManager.createEnvironmentVariable(ssisEnvironmentVariable);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }

    private static void create_folder_and_environment(SQLServerDataSource dataSource){
        SSISCatalogManager catalogManager = SSISCatalogManager.getInstance();
        SSISEnvironmentManager environmentManager = SSISEnvironmentManager.getInstance();
        try{
            String folderName = "test_folder";
            int folderId = catalogManager.createFolder(dataSource, folderName);
            System.out.printf("Created folder (%d, %s)\n", folderId, folderName);
            SSISEnvironment ssisEnvironment = new SSISEnvironmentBuilder()
                    .environmentName("my_test_environment")
                    .folderName(folderName)
                    .dataSource(dataSource)
                    .build();
            environmentManager.createEnvironment(ssisEnvironment);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    private static void createExecution(SQLServerDataSource dataSource) throws SQLException {
        SSISExecutionManager executionManager = SSISExecutionManager.getInstance();
        var ssisExecution = new SSISExecutionBuilder()
                .dataSource(dataSource)
                .folderName("projects")
                .projectName("myssisproject")
                .packageName("Package.dtsx")
                .ssisExecutionManager(executionManager)
                .build();

        SSISExecutionParameter<Boolean> param = new SSISExecutionParameterBuilder<Boolean>()
                .executionId(ssisExecution.getExecutionId())
                .objectType(50)
                .parameterName("SYNCHRONIZED")
                .parameterValue(true)
                .dataSource(dataSource)
                .build();

        SSISExecutionParameter<Integer> logging = new SSISExecutionParameterBuilder<Integer>()
                .executionId(ssisExecution.getExecutionId())
                .objectType(50)
                .parameterName("LOGGING_LEVEL")
                .parameterValue(3)
                .dataSource(dataSource)
                .build();

        executionManager.setExecutionParameter(param);
        executionManager.setExecutionParameter(logging);
        executionManager.startExecution(ssisExecution);
    }
}