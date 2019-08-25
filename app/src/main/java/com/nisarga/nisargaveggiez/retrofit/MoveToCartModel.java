package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoveToCartModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("session_id")
    public String session_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("count")
    public String count;

    public MoveToCartModel(String cust_id,String prd_id,String session)
    {
        this.customer_id = cust_id;
        this.product_id = prd_id;
        this.session_id  = session;
    }
    @SerializedName("result")
    public List<MoveToCartDatum> result=null;

    public class MoveToCartDatum
    {
        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("date_added")
        public String date_added;
    }
}
