package com.uberdoktor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfile {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("sms_verified_at")
    @Expose
    private String smsVerifiedAt;
    @SerializedName("shortcode")
    @Expose
    private String shortcode;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("document_no")
    @Expose
    private String documentNo;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("fee_charged")
    @Expose
    private Object feeCharged;
    @SerializedName("comission_uber")
    @Expose
    private Object comissionUber;
    @SerializedName("license_no")
    @Expose
    private Object licenseNo;
    @SerializedName("speciality")
    @Expose
    private Object speciality;
    @SerializedName("AvgRating")
    @Expose
    private Object avgRating;
    @SerializedName("languages")
    @Expose
    private Object languages;
    @SerializedName("image_url")
    @Expose
    private String imageUrl = null;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSmsVerifiedAt() {
        return smsVerifiedAt;
    }

    public void setSmsVerifiedAt(String smsVerifiedAt) {
        this.smsVerifiedAt = smsVerifiedAt;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getFeeCharged() {
        return feeCharged;
    }

    public void setFeeCharged(Object feeCharged) {
        this.feeCharged = feeCharged;
    }

    public Object getComissionUber() {
        return comissionUber;
    }

    public void setComissionUber(Object comissionUber) {
        this.comissionUber = comissionUber;
    }

    public Object getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(Object licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Object getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Object speciality) {
        this.speciality = speciality;
    }

    public Object getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Object avgRating) {
        this.avgRating = avgRating;
    }

    public Object getLanguages() {
        return languages;
    }

    public void setLanguages(Object languages) {
        this.languages = languages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}



