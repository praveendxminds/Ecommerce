package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsHomeTwo {

    @SerializedName("images")
    public List<tab> data = null;

    public class tab {

        @SerializedName("url")
        public String url;

        @SerializedName("title")
        public String title;

        @SerializedName("price")
        public String price;

        @SerializedName("qty")
        public String qty;

    }
}

