package com.uberdoktor.android.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uberdoktor.android.model.User;

public class RegisterResponse {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("curl_request_result")
    @Expose
    private String curlRequestResult;
    @SerializedName("token")
    @Expose
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCurlRequestResult() {
        return curlRequestResult;
    }

    public void setCurlRequestResult(String curlRequestResult) {
        this.curlRequestResult = curlRequestResult;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}