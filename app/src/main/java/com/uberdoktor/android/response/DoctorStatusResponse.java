package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorStatusResponse {

    @SerializedName("re")
    @Expose
    private String re;

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

}