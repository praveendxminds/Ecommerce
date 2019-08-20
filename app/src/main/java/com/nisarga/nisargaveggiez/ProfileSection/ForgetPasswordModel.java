package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForgetPasswordModel {

    @SerializedName("email_or_mobile")
    public String email_or_mobile;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<Datum> resultdata = null;

    public ForgetPasswordModel(String strInput) {
        this.email_or_mobile = strInput;
    }

    public class Datum {

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("telephone")
        public String telephone;

        @SerializedName("email")
        public String email;
    }
}
