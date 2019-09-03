package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductFilterModel
{

    @SerializedName("status")
    @Expose
    public  String status;

    @SerializedName("filter_low_to_high")
    @Expose
    public  int mfilter_low_to_high;

    @SerializedName("filter_high_to_low")
    @Expose
    public int mfilter_high_to_low;

    @SerializedName("filter_newest")
    @Expose
    public int mfilter_newest;

    @SerializedName("filter_popularity")
    @Expose
    public int mfilter_popularity;

    @SerializedName("min_price")
    @Expose
    public int mmin_price;


    @SerializedName("max_price")
    @Expose
    public int mmax_price;

    public ProductFilterModel(int filter_low_to_high, int filter_high_to_low,int filter_newest,int filter_popularity,int min_price,int max_price)
    {
        mfilter_low_to_high=filter_low_to_high;
       mfilter_high_to_low=filter_high_to_low;
        mfilter_newest=filter_newest;
        mfilter_popularity=filter_popularity;
        mmin_price=min_price;
        mmax_price=max_price;
    }

}
