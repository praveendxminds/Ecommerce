package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrderFeedback
{


    @SerializedName("status")
    public  String status;

    @SerializedName("message")
    public  String message;


    @SerializedName("order_id")
    public  String order_id;

    @SerializedName("customer_id")
    public  String customer_id;

    @SerializedName("rating")
    public  String rating;

    @SerializedName("feedback")
    public  String feedback;


    public OrderFeedback(String delivery_date)
    {
        this.order_id=order_id;
        this.customer_id=customer_id;
        this.rating=rating;
        this.feedback=feedback;
    }


}
