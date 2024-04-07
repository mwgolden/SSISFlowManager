package com.wgolden.SSISDB.Builder;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Pojo.SSISExecution;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;

public class SSISExecutionParameterBuilder<T> {
    private long executionId;
    private int objectType;
    private String parameterName;
    private T parameterValue;
    private SQLServerDataSource dataSource;

    public SSISExecutionParameterBuilder<T> ssisExecution(SSISExecution ssisExecution) {
        this.executionId = ssisExecution.getExecutionId();
        this.dataSource = ssisExecution.getDataSource();
        return this;
    }
    public SSISExecutionParameterBuilder<T> objectType(int objectType) {
        this.objectType = objectType;
        return this;
    }
    public SSISExecutionParameterBuilder<T> parameterName(String parameterName) {
        this.parameterName = parameterName;
        return this;
    }
    public SSISExecutionParameterBuilder<T> parameterValue(T parameterValue) {
        this.parameterValue = parameterValue;
        return this;
    }
    public SSISExecutionParameter<T> build(){
        return new SSISExecutionParameter<T>(
                this.executionId,
                this.objectType,
                this.parameterName,
                this.parameterValue,
                this.dataSource
        );
    }

}
