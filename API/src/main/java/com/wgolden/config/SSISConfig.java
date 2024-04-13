package com.wgolden.config;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Manager.SSISCatalogManager;
import com.wgolden.SSISDB.Manager.SSISEnvironmentManager;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.SSISEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SSISConfig {

    @Bean
    public SQLServerDataSource getDatasource(){
        var datasource = new SQLServerDataSource();
        datasource.setServerName("192.168.1.139");
        datasource.setPortNumber(1433);
        datasource.setDatabaseName("SSISDB");
        datasource.setUser(System.getenv("MSSQLUSER"));
        datasource.setPassword(System.getenv("MSSQLPASS"));
        datasource.setIntegratedSecurity(true);
        datasource.setTrustServerCertificate(true);
        datasource.setAuthenticationScheme("NTLM");
        return datasource;
    }
    @Bean
    public SSISCatalogManager getCatalogManager(){
        return SSISCatalogManager.getInstance();
    }
    @Bean
    public SSISEnvironmentManager getEnvironmentManager(){
        return SSISEnvironmentManager.getInstance();
    }
    @Bean
    public SSISExecutionManager getExecutionManager(){
        return SSISExecutionManager.getInstance();
    }
}
