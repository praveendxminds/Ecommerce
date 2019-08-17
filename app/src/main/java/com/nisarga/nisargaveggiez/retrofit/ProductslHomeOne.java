package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductslHomeOne {



    @SerializedName("images")
    public List<products> data = null;


    @SerializedName("slider")
    public List<imageslider> resultdata = null;

    public class products
    {


        @SerializedName("url")
        public String url;

        @SerializedName("title")
        public String title;

        @SerializedName("price")
        public String price;

        @SerializedName("qty")
        public String qty;

    }

    public class imageslider {

        @SerializedName("slid")
        public int slid;

        @SerializedName("title")
        public String title;

        @SerializedName("slimg")
        public String slimg;

    }





}

