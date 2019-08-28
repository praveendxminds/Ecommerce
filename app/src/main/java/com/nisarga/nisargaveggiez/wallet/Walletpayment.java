package com.nisarga.nisargaveggiez.wallet;

import com.google.gson.annotations.SerializedName;


public class Walletpayment {

    @SerializedName("status")
    public String status;

    @SerializedName("status")
    public String restatus;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("order_id")
    public String order_id;


    public Walletpayment(String status, String customer_id, String order_id) {
        this.status = status;
        this.customer_id = customer_id;
        this.order_id = order_id;
    }


}