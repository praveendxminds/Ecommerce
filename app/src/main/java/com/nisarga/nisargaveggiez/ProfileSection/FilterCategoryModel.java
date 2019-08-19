package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

public class FilterCategoryModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("filter_popularity")
    public int filter_popularity;

    @SerializedName("filter_low_to_high")
    public int filter_low_to_high;

    @SerializedName("filter_high_to_low")
    public int filter_high_to_low;

    @SerializedName("filter_newest")
    public int filter_newest;

    @SerializedName("min_price")
    public String min_price;

    @SerializedName("max_price")
    public String max_price;

    public FilterCategoryModel(int sFilterPopularity, int sFilterLowToHigh, int sFilterHighToLow, int sFilterNewestFirst,
                               String minPrice, String maxPrice) {

        this.filter_popularity = sFilterPopularity;
        this.filter_low_to_high = sFilterLowToHigh;
        this.filter_high_to_low = sFilterHighToLow;
        this.filter_newest = sFilterNewestFirst;
        this.min_price = minPrice;
        this.max_price = maxPrice;
    }
}
