package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterCategoryModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("count")
    public String count;

    @SerializedName("filter_popularity")
    public String filter_popularity;

    @SerializedName("filter_low_to_high")
    public String filter_low_to_high;

    @SerializedName("filter_high_to_low")
    public String filter_high_to_low;

    @SerializedName("filter_newest")
    public String filter_newest;

    @SerializedName("min_price")
    public String min_price;

    @SerializedName("max_price")
    public String max_price;

    @SerializedName("result")
    public List<Datum> resultdata = null;

    public FilterCategoryModel(String cust_id, String sFilterPopularity, String sFilterLowToHigh, String sFilterHighToLow,
                               String sFilterNewestFirst, String minPrice, String maxPrice) {

        this.customer_id = cust_id;
        this.filter_popularity = sFilterPopularity;
        this.filter_low_to_high = sFilterLowToHigh;
        this.filter_high_to_low = sFilterHighToLow;
        this.filter_newest = sFilterNewestFirst;
        this.min_price = minPrice;
        this.max_price = maxPrice;
    }

    public class Datum {

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("wishlist_status")
        public String wishlist_status;

        @SerializedName("Add_product_quantity_in_cart")
        public String add_product_quantity_in_cart;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("name")
        public String name;

        @SerializedName("weight_classes")
        public Object weight_classes;

        @SerializedName("options")
        public Object options;
    }
}
