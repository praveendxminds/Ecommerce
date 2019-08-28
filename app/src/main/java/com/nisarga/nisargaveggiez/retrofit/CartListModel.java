package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("products")
    public List<CartListDatum> result = null;

    public CartListModel(String cust_id) {
        this.customer_id = cust_id;
    }

    public class CartListDatum {

        @SerializedName("cart_id")
        public String cart_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("name")
        public String name;

        @SerializedName("option_name")
        public String option_name;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("price")
        public String price;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("wishlist")
        public String wishlist;

        @SerializedName("stock")
        public String stock;

        @SerializedName("shipping")
        public String shipping;

        @SerializedName("total")
        public String total;

        @SerializedName("reward")
        public String reward;

    }

    @SerializedName("totals")
    public List<TotalsDatum> totals = null;

    public class TotalsDatum {

        @SerializedName("title")
        public String title;

        @SerializedName("text")
        public String text;

    }

    @SerializedName("vouchers")
    public List<VouchersDatum> vouchers = null;

    public class VouchersDatum {

    }
}
