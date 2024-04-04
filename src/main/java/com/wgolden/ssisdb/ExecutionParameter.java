package com.wgolden.ssisdb;

public class ExecutionParameter {
    public int executionId;
    public int objectType;
    public String parameterName;
    public String parameterValue;

    public ExecutionParameter(int executionId, int objectType, String parameterName, String parameterValue) {
        this.executionId = executionId;
        this.objectType = objectType;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }
}
