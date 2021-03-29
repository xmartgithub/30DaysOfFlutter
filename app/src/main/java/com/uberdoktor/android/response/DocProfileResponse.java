package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.DocProfileData;
import com.uberdoktor.android.model.OwnData;

public class DocProfileResponse {
    @SerializedName("own_data")
    @Expose
    private DocProfileData ownData;

    public DocProfileData getOwnData() {
        return ownData;
    }

    public void setOwnData(DocProfileData ownData) {
        this.ownData = ownData;
    }
}
