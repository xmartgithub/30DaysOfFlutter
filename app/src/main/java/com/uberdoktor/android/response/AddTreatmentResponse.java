package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.uberdoktor.android.model.Patient;

public class AddTreatmentResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient")
    @Expose
    private List<Patient> patient = null;
    @SerializedName("appointment_id")
    @Expose
    private String appointmentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Patient> getPatient() {
        return patient;
    }

    public void setPatient(List<Patient> patient) {
        this.patient = patient;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

}
