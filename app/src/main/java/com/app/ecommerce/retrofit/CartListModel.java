package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("result")
    public List<CartListDatum> result=null;

    public CartListModel(String cust_id)
    {
        this.customer_id=cust_id;
    }

    public class CartListDatum{

        @SerializedName("product_id")
        public String prd_id;

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public  String qty;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("discount_price")
        public String discount_price;
    }
}
