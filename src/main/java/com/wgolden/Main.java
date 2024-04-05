package com.wgolden;

import com.microsoft.sqlserver.jdbc.*;
import com.wgolden.ssisdb.SSISExecutionManager;
import com.wgolden.ssisdb.SSISExecutionBuilder;
import com.wgolden.ssisdb.SSISExecutionParameter;
import com.wgolden.ssisdb.SSISExecutionParameterBuilder;

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
        try {
            var ssisExecution = new SSISExecutionBuilder()
                    .dataSource(datasource)
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
                    .dataSource(datasource)
                    .build();

            SSISExecutionParameter<Integer> logging = new SSISExecutionParameterBuilder<Integer>()
                    .executionId(ssisExecution.getExecutionId())
                    .objectType(50)
                    .parameterName("LOGGING_LEVEL")
                    .parameterValue(3)
                    .dataSource(datasource)
                    .build();

            executionManager.setExecutionParameter(param);
            executionManager.setExecutionParameter(logging);
            executionManager.startExecution(ssisExecution);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}