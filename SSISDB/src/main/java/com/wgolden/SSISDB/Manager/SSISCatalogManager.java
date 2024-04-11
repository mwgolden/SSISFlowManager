package com.wgolden.SSISDB.Manager;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class SSISCatalogManager {

    private static SSISCatalogManager instance;
    private SSISCatalogManager(){}
    public static SSISCatalogManager getInstance(){
        if(instance == null){
            instance = new SSISCatalogManager();
        }
        return instance;
    }

    public int createFolder(SQLServerDataSource dataSource, String folderName) throws RuntimeException{
        String createFolderStmt = """
                    EXECUTE [catalog].[create_folder]
                       @folder_name = ?
                      ,@folder_id = ?
                """;
        int folderId = -1;
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(createFolderStmt);
        ){
            stmt.setString(1, folderName);
            stmt.registerOutParameter(2, folderId);
            stmt.execute();
            folderId = stmt.getInt(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return folderId;
    }
    public void deleteFolder(SQLServerDataSource dataSource, String folderName) throws RuntimeException{
        String createFolderStmt = """
                    EXECUTE [catalog].[delete_folder]
                       @folder_name = ?
                """;
        try(Connection conn = dataSource.getConnection();
            CallableStatement stmt = conn.prepareCall(createFolderStmt);
        ){
            stmt.setString(1, folderName);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
