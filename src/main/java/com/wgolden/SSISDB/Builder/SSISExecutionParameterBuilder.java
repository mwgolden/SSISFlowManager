package com.wgolden.SSISDB.Builder;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;

public class SSISExecutionParameterBuilder<T> {
    private long executionId;
    private int objectType;
    private String parameterName;
    private T parameterValue;
    private SQLServerDataSource dataSource = null;

    public SSISExecutionParameterBuilder<T> executionId(long executionId) {
        this.executionId = executionId;
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
    public SSISExecutionParameterBuilder<T> dataSource(SQLServerDataSource dataSource) {
        this.dataSource = dataSource;
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
