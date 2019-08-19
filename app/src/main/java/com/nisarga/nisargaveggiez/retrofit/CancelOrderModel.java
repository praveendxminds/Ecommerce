package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class CancelOrderModel {

    @SerializedName("order_id")
    public String order_id;

    public CancelOrderModel(String ordId)
    {
        this.order_id = ordId;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

}
