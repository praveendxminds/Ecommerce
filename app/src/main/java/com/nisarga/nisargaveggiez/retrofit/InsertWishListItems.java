package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InsertWishListItems {
    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("product_id")
    public  String product_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("count")
    public String count;

    public InsertWishListItems(String cust_id,String prd_id)
    {
        this.customer_id = cust_id;
        this.product_id = prd_id;
    }
    @SerializedName("result")
    public List<InsertItemDatum> result=null;

    public class InsertItemDatum{

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("date_added")
        public String date_added;
    }
}
