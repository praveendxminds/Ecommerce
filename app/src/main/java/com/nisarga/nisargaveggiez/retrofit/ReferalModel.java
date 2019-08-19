package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ReferalModel {


    @SerializedName("status")
    public String status;

    @SerializedName("customer_id")
    public Integer customer_id;


    @SerializedName("data")
    public List<ReferalModelDatum> result=null;

    public ReferalModel(Integer customer_id) {
        this.customer_id = customer_id;
    }


    public class ReferalModelDatum
    {

        @SerializedName("referal_code")
        public String referal_code;

    }



}