package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsModel {

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public List<Datum> result = null;

    @SerializedName("similar_product")
    public List<SimilarProductDatum> similarProduct = null;

    public ProductDetailsModel(String cust_id, String prd_id) {
        this.customer_id = cust_id;
        this.product_id = prd_id;
    }

    public class Datum {

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("name")
        public String name;

        @SerializedName("wishlist_status")
        public String wishlist_status;

        @SerializedName("Add_product_quantity_in_cart")
        public String add_prd_qty;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("image")
        public List<ImageArr> image = null;

        @SerializedName("price")
        public String price;

        @SerializedName("reward")
        public String reward;

        @SerializedName("points")
        public String points;

        @SerializedName("reviews")
        public String reviews;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("description")
        public String description;

        public class ImageArr {

            @SerializedName("image")
            public String image_1;
        }
    }

    public class SimilarProductDatum {

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

        @SerializedName("date_added")
        public String date_added;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("name")
        public String name;
    }
}
