package com.wgolden.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatasourceConfig {

    @Bean
    public SQLServerDataSource getDatasource(){
        var datasource = new SQLServerDataSource();
        datasource.setServerName("");
        datasource.setPortNumber(1433);
        datasource.setDatabaseName("SSISDB");
        datasource.setUser(System.getenv("MSSQLUSER"));
        datasource.setPassword(System.getenv("MSSQLPASS"));
        datasource.setIntegratedSecurity(true);
        datasource.setTrustServerCertificate(true);
        datasource.setAuthenticationScheme("NTLM");
        return datasource;
    }
}
