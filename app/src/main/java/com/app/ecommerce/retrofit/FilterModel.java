package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterModel {

    @SerializedName("filter_low_to_high")
    public String filter_low_to_high;

    @SerializedName("filter_high_to_low")
    public String filter_high_to_low;

    @SerializedName("filter_newest")
    public String filter_newest;

    @SerializedName("filter_popularity")
    public String filter_popularity;

    @SerializedName("min_price")
    public String min_price;

    @SerializedName("max_price")
    public String max_price;

    public FilterModel(String low_to_high,String high_to_low,String newest,String popularity,String minprice,String maxprice)
    {
        this.filter_low_to_high = low_to_high;
        this.filter_high_to_low = high_to_low;
        this.filter_newest = newest;
        this.filter_popularity = popularity;
        this.min_price = minprice;
        this.max_price = maxprice;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<FilterDatum> result=null;

    public class FilterDatum{
        @SerializedName("product_id")
        public String product_id;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("name")
        public String name;

        @SerializedName("date_available")
        public String date_available;

        @SerializedName("updated_price")
        public String updated_price;

        @SerializedName("weight")
        public String weight;

        @SerializedName("length")
        public String length;

        @SerializedName("width")
        public String width;

        @SerializedName("height")
        public String height;

    }
}
