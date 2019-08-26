package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class CancelOrderModel {


    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("order_id")
    public String order_id;

    public CancelOrderModel(String ordId,String custId)
    {
        this.order_id = ordId;
        this.customer_id = custId;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

}
