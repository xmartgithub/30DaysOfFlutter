package com.uberdoktor.android.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.City;

public class MedicineRequestResponse {

    @SerializedName("cities")
    @Expose
    private List<City> cities = null;
    @SerializedName("checkup_id")
    @Expose
    private String checkupId;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public String getCheckupId() {
        return checkupId;
    }

    public void setCheckupId(String checkupId) {
        this.checkupId = checkupId;
    }

}