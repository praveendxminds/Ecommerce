package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarProductsModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("product_id")
    public String productid;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("result")
    public List<SimilarPrdDatum> result;

    public SimilarProductsModel(String product_id, String customer_id) {
        this.productid = product_id;
        this.customer_id = customer_id;
    }

    public static class SimilarPrdDatum {

        @SerializedName("related_id")
        public String related_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("wishlist_status")
        public String wishlist_status;

        @SerializedName("Add_product_quantity_in_cart")
        public String add_product_quantity_in_cart;

        @SerializedName("price")
        public String price;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("name")
        public String name;

        @SerializedName("weight_classes")
        public Object weight_classes;

        @SerializedName("options")
        public Object options;
    }
}
