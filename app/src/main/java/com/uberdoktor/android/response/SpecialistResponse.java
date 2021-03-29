package com.uberdoktor.android.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.Doc;

public class SpecialistResponse {

    @SerializedName("docs")
    @Expose
    private List<List<Doc>> docs = null;

    public List<List<Doc>> getDocs() {
        return docs;
    }

    public void setDocs(List<List<Doc>> docs) {
        this.docs = docs;
    }

}