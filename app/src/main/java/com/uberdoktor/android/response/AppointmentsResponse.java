package com.uberdoktor.android.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.Booked;
import com.uberdoktor.android.model.Ongoing;
import com.uberdoktor.android.model.PendingRequest;

public class AppointmentsResponse {

    @SerializedName("pending_requests")
    @Expose
    private List<List<PendingRequest>> pendingRequests = null;
    @SerializedName("booked")
    @Expose
    private List<List<Booked>> booked = null;
    @SerializedName("ongoing")
    @Expose
    private List<List<Ongoing>> ongoing = null;

    public List<List<PendingRequest>> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<List<PendingRequest>> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public List<List<Booked>> getBooked() {
        return booked;
    }

    public void setBooked(List<List<Booked>> booked) {
        this.booked = booked;
    }

    public List<List<Ongoing>> getOngoing() {
        return ongoing;
    }

    public void setOngoing(List<List<Ongoing>> ongoing) {
        this.ongoing = ongoing;
    }


}