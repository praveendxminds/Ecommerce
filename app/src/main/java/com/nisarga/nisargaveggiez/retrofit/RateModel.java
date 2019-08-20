package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateModel
{

    @SerializedName("status")
    public  String status;

    @SerializedName("message")
    public String message;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("rate")
    public String rate;

    public RateModel(String customer_id,String rate)
    {
        this.customer_id=customer_id;
        this.rate=rate;
    }

}
