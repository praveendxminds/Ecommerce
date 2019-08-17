package com.nisarga.nisargaveggiez.retrofit;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by praveen on 14/11/18.
 */




public class ImageScroll {



    @SerializedName("images")
    public List<Images> data = null;


    public class Images{


        @SerializedName("url")
        public String imageUrl;

        @SerializedName("title")
        public String title;

        @SerializedName("price")
        public String price;

        @SerializedName("qty")
        public String qty;


    }





}
