package com.uberdoktor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("task_id")
    @Expose
    private Integer taskId;
    @SerializedName("p_id")
    @Expose
    private Integer pId;
    @SerializedName("d_id")
    @Expose
    private Integer dId;
    @SerializedName("doc_name")
    @Expose
    private String docName;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("treatment")
    @Expose
    private String treatment;
    @SerializedName("review_status")
    @Expose
    private Object reviewStatus;
    @SerializedName("medicine_order_status")
    @Expose
    private Object medicineOrderStatus;
    @SerializedName("lab_test_status")
    @Expose
    private Object labTestStatus;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public Integer getDId() {
        return dId;
    }

    public void setDId(Integer dId) {
        this.dId = dId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Object getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Object reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Object getMedicineOrderStatus() {
        return medicineOrderStatus;
    }

    public void setMedicineOrderStatus(Object medicineOrderStatus) {
        this.medicineOrderStatus = medicineOrderStatus;
    }

    public Object getLabTestStatus() {
        return labTestStatus;
    }

    public void setLabTestStatus(Object labTestStatus) {
        this.labTestStatus = labTestStatus;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

}