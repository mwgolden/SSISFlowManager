package com.wgolden.SSISDB.Pojo;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class SSISExecutionParameter<T> {
    private final long executionId;
    private final int objectType;
    private final String parameterName;
    private final T parameterValue;
    private final SQLServerDataSource dataSource;

    public SSISExecutionParameter(long executionId, int objectType, String parameterName, T parameterValue, SQLServerDataSource dataSource) {
        this.executionId = executionId;
        this.objectType = objectType;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.dataSource = dataSource;
    }

    public long getExecutionId() {
        return executionId;
    }

    public int getObjectType() {
        return objectType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public T getParameterValue() {
        return parameterValue;
    }

    public SQLServerDataSource getDataSource() {
        return dataSource;
    }
}
