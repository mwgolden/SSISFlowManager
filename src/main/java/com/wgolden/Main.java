package com.wgolden;

import com.microsoft.sqlserver.jdbc.*;
import com.wgolden.ssisdb.SSISDBManager;
import com.wgolden.ssisdb.SSISExecutionBuilder;

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

        SSISDBManager ssisdbManager = SSISDBManager.getInstance();
        try {
            var ssisExecution = new SSISExecutionBuilder()
                    .dataSource(datasource)
                    .folderName("projects")
                    .projectName("myssisproject")
                    .packageName("Package.dtsx")
                    .createExecution(ssisdbManager)
                    .build();

            ssisdbManager.startExecution(ssisExecution);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}