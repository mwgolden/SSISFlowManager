package com.wgolden.ETLMetadataEntities;

public class SSISVariableValue {
    public int ssisVariableValueId;
    public int ssisVariableId;
    public int ssisEnvironmentId;
    public String value;
    public int externalSSISVariableValueId;

    public int getSsisVariableValueId() {
        return ssisVariableValueId;
    }

    public void setSsisVariableValueId(int ssisVariableValueId) {
        this.ssisVariableValueId = ssisVariableValueId;
    }

    public int getSsisVariableId() {
        return ssisVariableId;
    }

    public void setSsisVariableId(int ssisVariableId) {
        this.ssisVariableId = ssisVariableId;
    }

    public int getSsisEnvironmentId() {
        return ssisEnvironmentId;
    }

    public void setSsisEnvironmentId(int ssisEnvironmentId) {
        this.ssisEnvironmentId = ssisEnvironmentId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getExternalSSISVariableValueId() {
        return externalSSISVariableValueId;
    }

    public void setExternalSSISVariableValueId(int externalSSISVariableValueId) {
        this.externalSSISVariableValueId = externalSSISVariableValueId;
    }
}
