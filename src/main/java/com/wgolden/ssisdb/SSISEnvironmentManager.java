package com.wgolden.ssisdb;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

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
}
