package com.wgolden.SSISDB.Pojo;

public class SSISEnvironmentVariable<T> {
    private final SSISEnvironment environment;
    private final String variableName;
    private final SSISEnvironmentVariableDatatype dataType;
    private final boolean isSensitive;
    private final T value;
    private final String description;

    public SSISEnvironmentVariable(
            SSISEnvironment environment,
            String variableName,
            SSISEnvironmentVariableDatatype dataType,
            boolean isSensitive,
            T value,
            String description) {
        this.environment = environment;
        this.variableName = variableName;
        this.dataType = dataType;
        this.isSensitive = isSensitive;
        this.value = value;
        this.description = description;
    }

    public SSISEnvironment getEnvironment() {
        return environment;
    }

    public String getVariableName() {
        return variableName;
    }

    public SSISEnvironmentVariableDatatype getDataType() {
        return dataType;
    }

    public boolean isSensitive() {
        return isSensitive;
    }

    public T getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}