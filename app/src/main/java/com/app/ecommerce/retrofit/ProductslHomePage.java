package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductslHomePage {

    @SerializedName("status")
    public String string;

    @SerializedName("result")
    public List<DealOfDayList> data = null;

    public class DealOfDayList {


        @SerializedName("product_id")
        public String prd_id;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public String qty;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

    }



}

