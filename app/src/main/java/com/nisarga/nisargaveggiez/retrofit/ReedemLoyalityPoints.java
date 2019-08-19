package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class ReedemLoyalityPoints {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("points")
    public String points;

    public ReedemLoyalityPoints(String custId, String str_points) {
        this.customer_id = custId;
        this.points = str_points;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;
}
