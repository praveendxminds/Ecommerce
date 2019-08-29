package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuantityList {

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<Datum> data = null;

    public QuantityList(String ProductId) {
        this.product_id = ProductId;
    }

    public class Datum {

        @SerializedName("product_option_value_id")
        public String product_option_value_id;

        @SerializedName("product_option_id")
        public String product_option_id;

        @SerializedName("name")
        public String name;

        @SerializedName("price")
        public String price;

        @SerializedName("discount_price")
        public String discount_price;
    }
}
