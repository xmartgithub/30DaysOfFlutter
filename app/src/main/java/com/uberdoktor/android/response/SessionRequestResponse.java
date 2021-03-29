package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.AppointmentInfo;
import com.uberdoktor.android.model.AppointmentInfoList;

public class SessionRequestResponse {
    @SerializedName("appointment_info")
    @Expose
    private AppointmentInfo appointmentInfo;

    public AppointmentInfo getAppointmentInfo() {
        return appointmentInfo;
    }

    public void setAppointmentInfo(AppointmentInfo appointmentInfo) {
        this.appointmentInfo = appointmentInfo;
    }
}
