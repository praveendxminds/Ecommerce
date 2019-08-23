package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrderFeedback
{

    @SerializedName("delivery_date")
    public  String delivery_date;

    @SerializedName("success")
    public  String success;

    @SerializedName("status")
    public  String status;

    @SerializedName("data")
    public List<AddOrderDatum> result=null;

    public OrderFeedback(String delivery_date)
    {
        this.delivery_date=delivery_date;
    }

    public class AddOrderDatum
    {
        @SerializedName("address")
        public String address;

        @SerializedName("savings")
        public String savings;

        @SerializedName("delivery_charges")
        public String delivery_charges;

        @SerializedName("total")
        public String total;

    }

}
