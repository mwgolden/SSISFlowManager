package com.wgolden.ssisdb;

public class SSISEnvironmentVariableBuilder<T> {
    private  SSISEnvironment ssisEnvironment;
    private  String variableName;
    private  SSISEnvironmentVariableDatatype variableDatatype;
    private boolean isSensitive;
    private T variableValue;
    private  String description;

    public SSISEnvironmentVariableBuilder<T> ssisEnvironment(SSISEnvironment ssisEnvironment){
        this.ssisEnvironment = ssisEnvironment;
        return this;
    }
    public SSISEnvironmentVariableBuilder<T> variableName(String variableName){
        this.variableName = variableName;
        return this;
    }
    public SSISEnvironmentVariableBuilder<T> variableDataType(SSISEnvironmentVariableDatatype variableDatatype){
        this.variableDatatype = variableDatatype;
        return this;
    }
    public SSISEnvironmentVariableBuilder<T> isSensitive(boolean isSensitive){
        this.isSensitive = isSensitive;
        return this;
    }
    public SSISEnvironmentVariableBuilder<T> variableValue(T variableValue){
        this.variableValue = variableValue;
        return this;
    }
    public SSISEnvironmentVariableBuilder<T> description(String description){
        this.description = description;
        return this;
    }
    public SSISEnvironmentVariable<T> build(){
        return new SSISEnvironmentVariable<T>(
                this.ssisEnvironment,
                this.variableName,
                this.variableDatatype,
                this.isSensitive,
                this.variableValue,
                this.description
        );
    }
}
