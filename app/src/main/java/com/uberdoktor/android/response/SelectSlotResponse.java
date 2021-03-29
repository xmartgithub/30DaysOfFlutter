package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.AppointmentDetail;

public class SelectSlotResponse {

    @SerializedName("pending_payment_id")
    @Expose
    private Integer pendingPaymentId;
    @SerializedName("appointment_details")
    @Expose
    private List<AppointmentDetail> appointmentDetails = null;

    public Integer getPendingPaymentId() {
        return pendingPaymentId;
    }

    public void setPendingPaymentId(Integer pendingPaymentId) {
        this.pendingPaymentId = pendingPaymentId;
    }

    public List<AppointmentDetail> getAppointmentDetails() {
        return appointmentDetails;
    }

    public void setAppointmentDetails(List<AppointmentDetail> appointmentDetails) {
        this.appointmentDetails = appointmentDetails;
    }

}