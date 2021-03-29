package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalHistoryResponse {

    @SerializedName("medical_record")
    @Expose
    private List<Object> medicalRecord = null;

    public List<Object> getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(List<Object> medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

}