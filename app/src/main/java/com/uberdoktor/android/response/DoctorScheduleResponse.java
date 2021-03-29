package com.uberdoktor.android.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.Slot;

public class DoctorScheduleResponse {

    @SerializedName("slot")
    @Expose
    private List<Slot> slot = null;
    @SerializedName("doc_name")
    @Expose
    private String docName;
    @SerializedName("doc_fee")
    @Expose
    private String docFee;

    public List<Slot> getSlot() {
        return slot;
    }

    public void setSlot(List<Slot> slot) {
        this.slot = slot;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocFee() {
        return docFee;
    }

    public void setDocFee(String docFee) {
        this.docFee = docFee;
    }
}