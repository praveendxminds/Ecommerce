package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListModel {


    @SerializedName("products")
    public List<CartListDatum> result = null;


    public class CartListDatum {

        @SerializedName("cart_id")
        public String cart_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("name")
        public String name;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("model")
        public String model;


        @SerializedName("quantity")
        public String quantity;

        @SerializedName("wishlist")
        public String wishlist;

        @SerializedName("shipping")
        public String shipping;

        @SerializedName("price")
        public String price;

        @SerializedName("total")
        public String total;

        @SerializedName("reward")
        public String reward;


//        @SerializedName("option")
//        public List<CartListOption> options = null;
//
//        public class CartListOption
//        {
//
//            @SerializedName("product_option_id")
//            public String product_option_id;
//
//            @SerializedName("product_option_value_id")
//            public String product_option_value_id;
//
//            @SerializedName("name")
//            public String name;
//
//            @SerializedName("value")
//            public String value;
//
//            @SerializedName("type")
//            public String type;
//
//        }
    }




    @SerializedName("totals")
    public List<TotalsDatum> totals = null;


    public class TotalsDatum
    {
        @SerializedName("title")
        public String title;

        @SerializedName("text")
        public String text;

    }

}
