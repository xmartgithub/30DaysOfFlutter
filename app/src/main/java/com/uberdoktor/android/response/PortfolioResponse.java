package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.DoctorProfile;

public class PortfolioResponse {

    @SerializedName("doc_profile")
    @Expose
    private List<List<DoctorProfile>> docProfile = null;

    public List<List<DoctorProfile>> getDocProfile() {
        return docProfile;
    }

    public void setDocProfile(List<List<DoctorProfile>> docProfile) {
        this.docProfile = docProfile;
    }

}