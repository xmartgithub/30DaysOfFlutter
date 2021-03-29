package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorEarningsResponse {

    @SerializedName("earnings")
    @Expose
    private Integer earnings;
    @SerializedName("total_patients")
    @Expose
    private Integer totalPatients;

    public Integer getEarnings() {
        return earnings;
    }

    public void setEarnings(Integer earnings) {
        this.earnings = earnings;
    }

    public Integer getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(Integer totalPatients) {
        this.totalPatients = totalPatients;
    }

}
