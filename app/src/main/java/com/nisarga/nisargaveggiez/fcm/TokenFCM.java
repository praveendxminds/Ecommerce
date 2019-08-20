package com.nisarga.nisargaveggiez.fcm;

import com.google.gson.annotations.SerializedName;

public class TokenFCM {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("token")
    public String token;


    public TokenFCM(String customer_id, String token) {
        this.customer_id = customer_id;
        this.token = token;
    }
}
