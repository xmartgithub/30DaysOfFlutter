package com.uberdoktor.android.model;

public class ConsultationDetailsModel {

    private String consultationName;
    private String consultationValue;

    public ConsultationDetailsModel(String consultationName, String consultationValue) {
        this.consultationName = consultationName;
        this.consultationValue = consultationValue;
    }

    public String getConsultationName() {
        return consultationName;
    }

    public void setConsultationName(String consultationName) {
        this.consultationName = consultationName;
    }

    public String getConsultationValue() {
        return consultationValue;
    }

    public void setConsultationValue(String consultationValue) {
        this.consultationValue = consultationValue;
    }
}
