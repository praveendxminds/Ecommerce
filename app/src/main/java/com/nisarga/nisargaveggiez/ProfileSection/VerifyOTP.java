package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

public class VerifyOTP {

    @SerializedName("otp")
    public String otp;

    @SerializedName("email_or_mobile")
    public String email_or_mobile;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    public VerifyOTP(String finalOtp, String telephone) {
        this.otp = finalOtp;
        this.email_or_mobile = telephone;
    }
}
