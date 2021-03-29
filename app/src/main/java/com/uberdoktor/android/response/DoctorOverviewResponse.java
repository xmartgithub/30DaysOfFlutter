package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.QueDetail;

public class DoctorOverviewResponse {

    @SerializedName("total_patients")
    @Expose
    private Integer totalPatients;
    @SerializedName("todays_que")
    @Expose
    private Integer todaysQue;
    @SerializedName("patient_checked_today")
    @Expose
    private Integer patientCheckedToday;
    @SerializedName("que_details")
    @Expose
    private List<List<QueDetail>> queDetails = null;

    public Integer getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(Integer totalPatients) {
        this.totalPatients = totalPatients;
    }

    public Integer getTodaysQue() {
        return todaysQue;
    }

    public void setTodaysQue(Integer todaysQue) {
        this.todaysQue = todaysQue;
    }

    public Integer getPatientCheckedToday() {
        return patientCheckedToday;
    }

    public void setPatientCheckedToday(Integer patientCheckedToday) {
        this.patientCheckedToday = patientCheckedToday;
    }

    public List<List<QueDetail>> getQueDetails() {
        return queDetails;
    }

    public void setQueDetails(List<List<QueDetail>> queDetails) {
        this.queDetails = queDetails;
    }

}