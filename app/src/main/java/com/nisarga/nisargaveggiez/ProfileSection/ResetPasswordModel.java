package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordModel {

    @SerializedName("new_password")
    public String new_password;

    @SerializedName("confirm_password")
    public String confirm_password;

    @SerializedName("email_or_mobile")
    public String email_or_mobile;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    public ResetPasswordModel(String sNewPass, String sConfPass, String telephone) {
        this.new_password = sNewPass;
        this.confirm_password = sConfPass;
        this.email_or_mobile = telephone;
    }
}
