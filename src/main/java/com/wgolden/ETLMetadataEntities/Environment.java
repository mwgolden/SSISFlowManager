package com.wgolden.ETLMetadataEntities;

public class Environment {
    public int environmentId;
    public String environmentCode;
    public String environment;

    public int getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(int environmentId) {
        this.environmentId = environmentId;
    }

    public String getEnvironmentCode() {
        return environmentCode;
    }

    public void setEnvironmentCode(String environmentCode) {
        this.environmentCode = environmentCode;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
