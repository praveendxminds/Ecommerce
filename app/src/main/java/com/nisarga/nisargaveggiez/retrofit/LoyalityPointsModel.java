package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class LoyalityPointsModel {

    @SerializedName("customer_id")
    public String customer_id;

    public LoyalityPointsModel(String custId) {
        this.customer_id = custId;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public String data;

}
