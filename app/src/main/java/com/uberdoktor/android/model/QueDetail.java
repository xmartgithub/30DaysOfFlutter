package com.uberdoktor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueDetail {

    @SerializedName("appointment_id")
    @Expose
    private Integer appointmentId;
    @SerializedName("patient_fname")
    @Expose
    private String patientFname;
    @SerializedName("patient_lname")
    @Expose
    private String patientLname;
    @SerializedName("appointment_date")
    @Expose
    private String appointmentDate;
    @SerializedName("slot")
    @Expose
    private String slot;

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientFname() {
        return patientFname;
    }

    public void setPatientFname(String patientFname) {
        this.patientFname = patientFname;
    }

    public String getPatientLname() {
        return patientLname;
    }

    public void setPatientLname(String patientLname) {
        this.patientLname = patientLname;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

}