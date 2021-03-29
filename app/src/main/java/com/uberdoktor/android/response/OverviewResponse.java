package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.User;

public class OverviewResponse {

    @SerializedName("checkup_data")
    @Expose
    private List<Object> checkupData = null;
    @SerializedName("own_data")
    @Expose
    private User userData;

    public List<Object> getCheckupData() {
        return checkupData;
    }

    public void setCheckupData(List<Object> checkupData) {
        this.checkupData = checkupData;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

}