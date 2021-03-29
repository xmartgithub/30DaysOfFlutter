package com.uberdoktor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booked {

    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("doctor_name")
    @Expose
    private String doctorName;
    @SerializedName("appointment_date")
    @Expose
    private String appointmentDate;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("appointment_id")
    @Expose
    private Integer appointmentId;
    @SerializedName("doctor_fees")
    @Expose
    private String appointmentFee;

    public String getAppointmentFee() {
        return appointmentFee;
    }

    public void setAppointmentFee(String appointmentFee) {
        this.appointmentFee = appointmentFee;
    }
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

}