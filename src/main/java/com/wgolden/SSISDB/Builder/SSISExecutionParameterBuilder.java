package com.wgolden.SSISDB.Builder;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.wgolden.SSISDB.Pojo.SSISExecution;
import com.wgolden.SSISDB.Pojo.SSISExecutionParameter;

/**
 * Builder class for constructing SSISExecutionParameter instances.
 * This builder facilitates the creation of SSISExecutionParameter objects with various parameters.
 *
 * @param <T> Type parameter specifying the data type of the parameter value.
 */
public class SSISExecutionParameterBuilder<T> {
    private long executionId;
    private int objectType;
    private String parameterName;
    private T parameterValue;
    private SQLServerDataSource dataSource;

    /**
     * Sets the SSIS execution associated with this parameter.
     *
     * @param ssisExecution The SSISExecution instance representing the SSIS execution.
     * @return The SSISExecutionParameterBuilder instance.
     */
    public SSISExecutionParameterBuilder<T> ssisExecution(SSISExecution ssisExecution) {
        this.executionId = ssisExecution.getExecutionId();
        this.dataSource = ssisExecution.getDataSource();
        return this;
    }
    /**
     * Sets the object type of the SSIS parameter.
     *
     * @param objectType The object type of the SSIS parameter.
     * @return The SSISExecutionParameterBuilder instance.
     */
    public SSISExecutionParameterBuilder<T> objectType(int objectType) {
        this.objectType = objectType;
        return this;
    }
    /**
     * Sets the name of the SSIS parameter.
     *
     * @param parameterName The name of the SSIS parameter.
     * @return The SSISExecutionParameterBuilder instance.
     */
    public SSISExecutionParameterBuilder<T> parameterName(String parameterName) {
        this.parameterName = parameterName;
        return this;
    }
    /**
     * Sets the value of the SSIS parameter.
     *
     * @param parameterValue The value of the SSIS parameter.
     * @return The SSISExecutionParameterBuilder instance.
     */
    public SSISExecutionParameterBuilder<T> parameterValue(T parameterValue) {
        this.parameterValue = parameterValue;
        return this;
    }
    /**
     * Builds and returns an SSISExecutionParameter instance.
     *
     * @return An SSISExecutionParameter instance with the specified parameters.
     */
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
