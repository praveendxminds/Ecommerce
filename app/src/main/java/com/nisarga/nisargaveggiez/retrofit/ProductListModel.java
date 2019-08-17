package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListModel {

    @SerializedName("status")
    public String status;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("result")
    public List<ProductListDatum> result=null;

    public ProductListModel(String custid)
    {
        this.customer_id = custid;
    }
    public class ProductListDatum{

        @SerializedName("product_id")
        public String prd_id;

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
