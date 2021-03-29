package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.CheckupDatum;
import com.uberdoktor.android.model.DoctorCheckupDatum;

public class DoctorConsultationResponse {

    @SerializedName("checkup_data")
    @Expose
    private List<DoctorCheckupDatum> checkupData = null;

    public List<DoctorCheckupDatum> getDoctorCheckupData() {
        return checkupData;
    }

    public void setDoctorCheckupData(List<DoctorCheckupDatum> checkupData) {
        this.checkupData = checkupData;
    }

}