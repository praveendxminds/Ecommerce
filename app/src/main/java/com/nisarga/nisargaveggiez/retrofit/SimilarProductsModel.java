package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarProductsModel {

    @SerializedName("product_id")
    public String productid;

    @SerializedName("customer_id")
    public String customer_id;

    public SimilarProductsModel(String product_id, String customer_id) {
        this.productid = product_id;
        this.customer_id = customer_id;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<SimilarPrdDatum> result;

    public static class SimilarPrdDatum {

        @SerializedName("related_id")
        public String related_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("name")
        public String name;

        @SerializedName("discount_price")
        public String discount_price;
    }
}
