package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class ReOrder {

    @SerializedName("order_id")
    public String order_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    public ReOrder(String ordId)
    {
        this.order_id = ordId;
    }
}
