package com.wgolden.ETLMetadataEntities;

public class PrecedenceConstraint {
    public int jobStepId;
    public int packageId;
    public int dependentOnJobStepId;
    public int dependentOnPackageId;
    public boolean loadedData;
    public boolean updatedData;
    public boolean deletedData;
    public boolean rejectedData;
    public boolean identifiedDataToUpdate;
    public boolean identifiedDataToDelete;
    public int externalJobStepId;
    public int externalPackageId;
    public int externalDependentOnJobStepId;
    public int externalDependentOnPackageId;

    public int getJobStepId() {
        return jobStepId;
    }

    public void setJobStepId(int jobStepId) {
        this.jobStepId = jobStepId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getDependentOnJobStepId() {
        return dependentOnJobStepId;
    }

    public void setDependentOnJobStepId(int dependentOnJobStepId) {
        this.dependentOnJobStepId = dependentOnJobStepId;
    }

    public int getDependentOnPackageId() {
        return dependentOnPackageId;
    }

    public void setDependentOnPackageId(int dependentOnPackageId) {
        this.dependentOnPackageId = dependentOnPackageId;
    }

    public boolean isLoadedData() {
        return loadedData;
    }

    public void setLoadedData(boolean loadedData) {
        this.loadedData = loadedData;
    }

    public boolean isUpdatedData() {
        return updatedData;
    }

    public void setUpdatedData(boolean updatedData) {
        this.updatedData = updatedData;
    }

    public boolean isDeletedData() {
        return deletedData;
    }

    public void setDeletedData(boolean deletedData) {
        this.deletedData = deletedData;
    }

    public boolean isRejectedData() {
        return rejectedData;
    }

    public void setRejectedData(boolean rejectedData) {
        this.rejectedData = rejectedData;
    }

    public boolean isIdentifiedDataToUpdate() {
        return identifiedDataToUpdate;
    }

    public void setIdentifiedDataToUpdate(boolean identifiedDataToUpdate) {
        this.identifiedDataToUpdate = identifiedDataToUpdate;
    }

    public boolean isIdentifiedDataToDelete() {
        return identifiedDataToDelete;
    }

    public void setIdentifiedDataToDelete(boolean identifiedDataToDelete) {
        this.identifiedDataToDelete = identifiedDataToDelete;
    }

    public int getExternalJobStepId() {
        return externalJobStepId;
    }

    public void setExternalJobStepId(int externalJobStepId) {
        this.externalJobStepId = externalJobStepId;
    }

    public int getExternalPackageId() {
        return externalPackageId;
    }

    public void setExternalPackageId(int externalPackageId) {
        this.externalPackageId = externalPackageId;
    }

    public int getExternalDependentOnJobStepId() {
        return externalDependentOnJobStepId;
    }

    public void setExternalDependentOnJobStepId(int externalDependentOnJobStepId) {
        this.externalDependentOnJobStepId = externalDependentOnJobStepId;
    }

    public int getExternalDependentOnPackageId() {
        return externalDependentOnPackageId;
    }

    public void setExternalDependentOnPackageId(int externalDependentOnPackageId) {
        this.externalDependentOnPackageId = externalDependentOnPackageId;
    }
}
