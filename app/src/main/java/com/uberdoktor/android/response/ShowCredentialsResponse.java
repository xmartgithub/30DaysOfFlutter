package com.uberdoktor.android.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.DoctorEducation;
import com.uberdoktor.android.model.DoctorSpeciality;

public class ShowCredentialsResponse {

    @SerializedName("doctor_education")
    @Expose
    private List<DoctorEducation> doctorEducation;
    @SerializedName("doctor_specialities")
    @Expose
    private List<DoctorSpeciality> doctorSpecialities;

    public List<DoctorEducation> getDoctorEducation() {
        return doctorEducation;
    }

    public void setDoctorEducation(List<DoctorEducation> doctorEducation) {
        this.doctorEducation = doctorEducation;
    }

    public List<DoctorSpeciality> getDoctorSpecialities() {
        return doctorSpecialities;
    }

    public void setDoctorSpecialities(List<DoctorSpeciality> doctorSpecialities) {
        this.doctorSpecialities = doctorSpecialities;
    }

}
