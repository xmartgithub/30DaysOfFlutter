package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDoctorScheduleResponse {

    @SerializedName("results")
    @Expose
    private String results;

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

}