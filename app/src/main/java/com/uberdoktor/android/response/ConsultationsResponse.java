package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsultationsResponse {

    @SerializedName("checkup_data")
    @Expose
    private String checkupData;

    public String getCheckupData() {
        return checkupData;
    }

    public void setCheckupData(String checkupData) {
        this.checkupData = checkupData;
    }

}