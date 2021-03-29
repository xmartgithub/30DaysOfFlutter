package com.uberdoktor.android.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.AppointmentInfoList;

public class VideoCallRequestResponse {

    @SerializedName("appointment_info")
    @Expose
    private List<AppointmentInfoList> appointmentInfoList = null;

    public List<AppointmentInfoList> getAppointmentInfoList() {
        return appointmentInfoList;
    }

    public void setAppointmentInfo(List<AppointmentInfoList> appointmentInfoList) {
        this.appointmentInfoList = appointmentInfoList;
    }

}