package com.wgolden.ETLMetadataEntities;

public class SSISParameter {
    public int ssisParameterId;
    public String ssisParameterName;
    public int ssisProjectId;
    public int ssisObjectTypeCode;
    public String ssisObjectName;
    public int ssisVariableId;
    public int externalSSISParameterId;

    public int getSsisParameterId() {
        return ssisParameterId;
    }

    public void setSsisParameterId(int ssisParameterId) {
        this.ssisParameterId = ssisParameterId;
    }

    public String getSsisParameterName() {
        return ssisParameterName;
    }

    public void setSsisParameterName(String ssisParameterName) {
        this.ssisParameterName = ssisParameterName;
    }

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public int getSsisObjectTypeCode() {
        return ssisObjectTypeCode;
    }

    public void setSsisObjectTypeCode(int ssisObjectTypeCode) {
        this.ssisObjectTypeCode = ssisObjectTypeCode;
    }

    public String getSsisObjectName() {
        return ssisObjectName;
    }

    public void setSsisObjectName(String ssisObjectName) {
        this.ssisObjectName = ssisObjectName;
    }

    public int getSsisVariableId() {
        return ssisVariableId;
    }

    public void setSsisVariableId(int ssisVariableId) {
        this.ssisVariableId = ssisVariableId;
    }

    public int getExternalSSISParameterId() {
        return externalSSISParameterId;
    }

    public void setExternalSSISParameterId(int externalSSISParameterId) {
        this.externalSSISParameterId = externalSSISParameterId;
    }
}
