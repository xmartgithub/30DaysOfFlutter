package com.uberdoktor.android.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.CheckupDatum;

public class PatientConsultationsResponse {

    @SerializedName("checkup_data")
    @Expose
    private List<CheckupDatum> checkupData = null;

    public List<CheckupDatum> getCheckupData() {
        return checkupData;
    }

    public void setCheckupData(List<CheckupDatum> checkupData) {
        this.checkupData = checkupData;
    }

}