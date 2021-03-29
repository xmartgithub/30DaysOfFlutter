package com.uberdoktor.android.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfile {

    @SerializedName("First_name")
    @Expose
    private String firstName;
    @SerializedName("Last_name")
    @Expose
    private String lastName;
    @SerializedName("Specialities")
    @Expose
    private List<String> specialities = null;
    @SerializedName("Education")
    @Expose
    private List<Object> education = null;
    @SerializedName("Consultation-fee")
    @Expose
    private Object consultationFee;
    @SerializedName("wait-time")
    @Expose
    private Integer waitTime;
    @SerializedName("today-schedule")
    @Expose
    private List<Object> todaySchedule = null;
    @SerializedName("ratings")
    @Expose
    private Object ratings;
    @SerializedName("Special-instructions")
    @Expose
    private List<Object> specialInstructions = null;
    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<String> specialities) {
        this.specialities = specialities;
    }

    public List<Object> getEducation() {
        return education;
    }

    public void setEducation(List<Object> education) {
        this.education = education;
    }

    public Object getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(Object consultationFee) {
        this.consultationFee = consultationFee;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public List<Object> getTodaySchedule() {
        return todaySchedule;
    }

    public void setTodaySchedule(List<Object> todaySchedule) {
        this.todaySchedule = todaySchedule;
    }

    public Object getRatings() {
        return ratings;
    }

    public void setRatings(Object ratings) {
        this.ratings = ratings;
    }

    public List<Object> getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(List<Object> specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

}