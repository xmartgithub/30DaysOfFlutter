package com.uberdoktor.android.model;

public class AddEducation {

    String degree;
    String year;

    public AddEducation(String degree, String year) {
        this.degree = degree;
        this.year = year;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDegree() {
        return degree;
    }

    public String getYear() {
        return year;
    }
}
