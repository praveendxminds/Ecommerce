package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListModel {


    @SerializedName("products")
    public List<CartListDatum> result=null;

    public CartListModel()
    {
    }

    public class CartListDatum{

        @SerializedName("cart_id")
        public String cart_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("name")
        public  String name;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("model")
        public String model;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("stock")
        public String stock;

        @SerializedName("shipping")
        public String shipping;

        @SerializedName("price")
        public String price;

        @SerializedName("total")
        public String total;

        @SerializedName("reward")
        public String reward;
    }
}
