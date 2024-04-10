package com.wgolden;

import com.microsoft.sqlserver.jdbc.*;
import com.wgolden.SSISDB.Builder.*;
import com.wgolden.SSISDB.Manager.SSISCatalogManager;
import com.wgolden.SSISDB.Manager.SSISEnvironmentManager;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.*;

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

        SSISExecutionManager executionManager = SSISExecutionManager.getInstance();
        // run a package and populate parameter at runtime without using environment
        SSISExecution ssisExecution = null;
        try {
            ssisExecution = new SSISExecutionBuilder()
                    .folderName("Projects")
                    .projectName("myssisproject")
                    .packageName("Package.dtsx")
                    .dataSource(datasource)
                    .ssisExecutionManager(executionManager)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SSISExecutionParameter<String> connectionString = new SSISExecutionParameterBuilder<String>()
                .ssisExecution(ssisExecution)
                .parameterValue("Data Source=localhost;Initial Catalog=AdventureWorks;Provider=SQLOLEDB.1;Integrated Security=SSPI;")
                .objectType(20)
                .parameterName("ConnectionString")
                .build();

        SSISExecutionParameter<Boolean> sync = new SSISExecutionParameterBuilder<Boolean>()
                .ssisExecution(ssisExecution)
                .objectType(50)
                .parameterName("SYNCHRONIZED")
                .parameterValue(true)
                .build();

        executionManager.setExecutionParameter(connectionString);
        executionManager.setExecutionParameter(sync);
        executionManager.startExecution(ssisExecution);


        //test_run(datasource);
    }
    private static void test_run(SQLServerDataSource datasource){
        /*
            Steps to
            1. Create ssis environment
            2. Create a new environment variable
            3. Create a reference to the new environment from a project
            4. Set a package parameter to an environment variable
            5. create an execution that references the environment
            6. start the ssis execution
         */
        SSISEnvironmentManager environmentManager = SSISEnvironmentManager.getInstance();
        try{
            String folderName = "Projects";
            // environment already exists, so we don't need to create it
            SSISEnvironment ssisEnvironment = new SSISEnvironmentBuilder()
                    .environmentName("my_test_environment")
                    .folderName(folderName)
                    .dataSource(datasource)
                    .build();

            environmentManager.createEnvironment(ssisEnvironment);
            var environmentVariable = new SSISEnvironmentVariableBuilder<String>()
                    .ssisEnvironment(ssisEnvironment)
                    .variableDataType(SSISEnvironmentVariableDatatype.String)
                    .variableName("ConnectionString")
                    .variableValue("Data Source=localhost;Initial Catalog=AdventureWorks;Provider=SQLOLEDB.1;Integrated Security=SSPI;")
                    .build();
            environmentManager.createEnvironmentVariable(environmentVariable);

            SSISEnvironmentReference environmentReference = new SSISEnvironmentReferenceBuilder()
                    .ssisEnvironment(ssisEnvironment)
                    .projectFolderName("Projects")
                    .projectName("myssisproject")
                    .ssisEnvironmentReferenceType(SSISEnvironmentReferenceType.R)
                    .ssisEnvironmentManager(environmentManager)
                    .build();

            // to connect an environment variable, the parameter value is the variable name, and value type is R
            environmentManager.setObjectParameterValue(folderName,
                    "myssisproject",
                    20,
                    "ConnectionString",
                    environmentVariable.getVariableName(),
                    datasource);

            SSISExecutionManager ssisExecutionManager = SSISExecutionManager.getInstance();


            SSISExecution ssisExecution = new SSISExecutionBuilder()
                    .ssisExecutionManager(ssisExecutionManager)
                    .dataSource(datasource)
                    .folderName(folderName)
                    .projectName("myssisproject")
                    .packageName("Package.dtsx")
                    .environmentReferenceId(environmentReference.getEnvironmentReferenceId())
                    .build();

            ssisExecutionManager.startExecution(ssisExecution);
        } catch (RuntimeException | SQLException e) {
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
                .objectType(50)
                .parameterName("SYNCHRONIZED")
                .parameterValue(true)
                .ssisExecution(ssisExecution)
                .build();

        SSISExecutionParameter<Integer> logging = new SSISExecutionParameterBuilder<Integer>()
                .objectType(50)
                .parameterName("LOGGING_LEVEL")
                .parameterValue(3)
                .ssisExecution(ssisExecution)
                .build();

        executionManager.setExecutionParameter(param);
        executionManager.setExecutionParameter(logging);
        executionManager.startExecution(ssisExecution);
    }
}