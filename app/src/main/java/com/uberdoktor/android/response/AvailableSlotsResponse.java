package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.Slot;

public class AvailableSlotsResponse {

    @SerializedName("slots")
    @Expose
    private List<List<Slot>> slots = null;

    public List<List<Slot>> getSlots() {
        return slots;
    }

    public void setSlots(List<List<Slot>> slots) {
        this.slots = slots;
    }

}