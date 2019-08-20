package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class EarnReferal {


    @SerializedName("status")
    public String status;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("referral_code")
    public String referral_code;


    public EarnReferal(String customer_id,String referral_code)
    {
        this.customer_id = customer_id;
        this.referral_code = referral_code;
    }





}