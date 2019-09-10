package com.nisarga.nisargaveggiez.wallet;

import com.google.gson.annotations.SerializedName;


public class Usewallet
{

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("total_to_be_paid")
    public int total_to_be_paid;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("order_id")
    public String order_id;

    @SerializedName("wallet")
    public String wallet;

    @SerializedName("online_payment_status")
    public String online_payment_status;


    public Usewallet(String customer_id, String order_id, String wallet,String pay_status)
    {
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.wallet = wallet;
        this.online_payment_status = pay_status;
    }


}