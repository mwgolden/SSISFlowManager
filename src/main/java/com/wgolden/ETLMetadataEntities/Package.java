package com.wgolden.ETLMetadataEntities;

public class Package {
    public int packageId;
    public String packageName;
    public boolean enabled;
    public int ssisProjectId;
    public boolean use32BitRuntime;
    public int catalogLoggingLevel;
    public String targetDatabase;
    public String targetSchema;
    public String targetTable;
    public boolean testCompletion;
    public boolean testLoad;
    public int externalPackageId;
    public boolean alternatePath;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSsisProjectId() {
        return ssisProjectId;
    }

    public void setSsisProjectId(int ssisProjectId) {
        this.ssisProjectId = ssisProjectId;
    }

    public boolean isUse32BitRuntime() {
        return use32BitRuntime;
    }

    public void setUse32BitRuntime(boolean use32BitRuntime) {
        this.use32BitRuntime = use32BitRuntime;
    }

    public int getCatalogLoggingLevel() {
        return catalogLoggingLevel;
    }

    public void setCatalogLoggingLevel(int catalogLoggingLevel) {
        this.catalogLoggingLevel = catalogLoggingLevel;
    }

    public String getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(String targetDatabase) {
        this.targetDatabase = targetDatabase;
    }

    public String getTargetSchema() {
        return targetSchema;
    }

    public void setTargetSchema(String targetSchema) {
        this.targetSchema = targetSchema;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public boolean isTestCompletion() {
        return testCompletion;
    }

    public void setTestCompletion(boolean testCompletion) {
        this.testCompletion = testCompletion;
    }

    public boolean isTestLoad() {
        return testLoad;
    }

    public void setTestLoad(boolean testLoad) {
        this.testLoad = testLoad;
    }

    public int getExternalPackageId() {
        return externalPackageId;
    }

    public void setExternalPackageId(int externalPackageId) {
        this.externalPackageId = externalPackageId;
    }

    public boolean isAlternatePath() {
        return alternatePath;
    }

    public void setAlternatePath(boolean alternatePath) {
        this.alternatePath = alternatePath;
    }
}
