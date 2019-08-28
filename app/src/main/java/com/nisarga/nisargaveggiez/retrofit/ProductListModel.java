package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListModel {

    @SerializedName("status")
    public String status;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("profile_pic")
    public String profile_pic;

    @SerializedName("total_product_count")
    public String total_product_count;

    @SerializedName("result")
    public List<ProductListDatum> result = null;

    public ProductListModel(String custid) {
        this.customer_id = custid;
    }

    public class ProductListDatum {

        @SerializedName("product_id")
        public String prd_id;

        @SerializedName("name")
        public String name;

        @SerializedName("Add_product_quantity_in_cart")
        public String add_product_quantity_in_cart;

        @SerializedName("wishlist_status")
        public String wishlist_status;

        @SerializedName("quantity")
        public String qty;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("weight_classes")
        public Object weight_classes;

        @SerializedName("options")
        public Object options;
    }
}
