package com.wgolden.services;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.Execution;
import com.wgolden.ExecutionParameter;
import com.wgolden.SSISDB.Builder.SSISExecutionBuilder;
import com.wgolden.SSISDB.Builder.SSISExecutionParameterBuilder;
import com.wgolden.SSISDB.Manager.SSISCatalogManager;
import com.wgolden.SSISDB.Manager.SSISEnvironmentManager;
import com.wgolden.SSISDB.Manager.SSISExecutionManager;
import com.wgolden.SSISDB.Pojo.SSISExecution;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class SsisService {
    private final SQLServerDataSource sqlServerDataSource;
    private final SSISCatalogManager ssisCatalogManager;
    private final SSISEnvironmentManager ssisEnvironmentManager;
    private final SSISExecutionManager ssisExecutionManager;

    public SsisService(
            SQLServerDataSource sqlServerDataSource,
            SSISCatalogManager ssisCatalogManager,
            SSISExecutionManager ssisExecutionManager,
            SSISEnvironmentManager ssisEnvironmentManager
    ){
        this.sqlServerDataSource = sqlServerDataSource;
        this.ssisCatalogManager = ssisCatalogManager;
        this.ssisEnvironmentManager = ssisEnvironmentManager;
        this.ssisExecutionManager = ssisExecutionManager;
    }
    public <T> long createSSISExecutionWithParams(Execution execution,  ArrayList<ExecutionParameter<T>> params) {
        SSISExecution ssisExecution = null;
        try {
            ssisExecution = new SSISExecutionBuilder()
                    .ssisExecutionManager(ssisExecutionManager)
                    .dataSource(sqlServerDataSource)
                    .folderName(execution.folderName())
                    .projectName(execution.projectName())
                    .packageName(execution.packageName())
                    .build();

            for(ExecutionParameter<T> param: params){
                SSISExecutionParameter<T> parameter = new SSISExecutionParameterBuilder<T>()
                        .ssisExecution(ssisExecution)
                        .objectType(param.objectType())
                        .parameterValue(param.parameterValue())
                        .parameterName(param.parameterName())
                        .build();
                ssisExecutionManager.setExecutionParameter(parameter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ssisExecution.getExecutionId();
    }

    public SQLServerDataSource getSqlServerDataSource() {
        return sqlServerDataSource;
    }

    public SSISCatalogManager getSsisCatalogManager() {
        return ssisCatalogManager;
    }

    public SSISEnvironmentManager getSsisEnvironmentManager() {
        return ssisEnvironmentManager;
    }

    public SSISExecutionManager getSsisExecutionManager() {
        return ssisExecutionManager;
    }
}
