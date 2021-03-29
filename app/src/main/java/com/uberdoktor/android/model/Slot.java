package com.uberdoktor.android.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slot {

    @SerializedName("doc_id")
    @Expose
    private String docId;
    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("schedule_start")
    @Expose
    private String scheduleStart;
    @SerializedName("schedule_end")
    @Expose
    private String scheduleEnd;
    @SerializedName("avail_slots")
    @Expose
    private List<String> availSlots = null;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(String scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public String getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(String scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    public List<String> getAvailSlots() {
        return availSlots;
    }

    public void setAvailSlots(List<String> availSlots) {
        this.availSlots = availSlots;
    }

}