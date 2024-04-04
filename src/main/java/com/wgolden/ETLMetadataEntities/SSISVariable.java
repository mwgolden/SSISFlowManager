package com.wgolden.ETLMetadataEntities;

public class SSISVariable {
    public int ssisVariableId;
    public String ssisVariableName;
    public int ssisProjectId;
    public String ssisVariableDescription;
    public String ssisDataType;
    public boolean sensitive;
    public int externalSSISVariableId;

    public int getSsisVariableId() {
        return ssisVariableId;
    }

    public void setSsisVariableId(int ssisVariableId) {
        this.ssisVariableId = ssisVariableId;
    }

    public String getSsisVariableName() {
        return ssisVariableName;
    }

    public void setSsisVariableName(String ssisVariableName) {
        this.ssisVariableName = ssisVariableName;
    }

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public String getSsisVariableDescription() {
        return ssisVariableDescription;
    }

    public void setSsisVariableDescription(String ssisVariableDescription) {
        this.ssisVariableDescription = ssisVariableDescription;
    }

    public String getSsisDataType() {
        return ssisDataType;
    }

    public void setSsisDataType(String ssisDataType) {
        this.ssisDataType = ssisDataType;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public int getExternalSSISVariableId() {
        return externalSSISVariableId;
    }

    public void setExternalSSISVariableId(int externalSSISVariableId) {
        this.externalSSISVariableId = externalSSISVariableId;
    }
}
