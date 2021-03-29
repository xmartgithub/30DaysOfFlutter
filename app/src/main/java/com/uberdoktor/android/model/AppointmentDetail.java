package com.uberdoktor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentDetail {
    @SerializedName("Doctor_name")
    @Expose
    private String doctorName = null;
    @SerializedName("Fee")
    @Expose
    private String fee;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }
}
