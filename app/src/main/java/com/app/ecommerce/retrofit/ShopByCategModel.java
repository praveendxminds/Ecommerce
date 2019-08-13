package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShopByCategModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<ResultDatum> result = null;

    public class ResultDatum {

        @SerializedName("category_id")
        public String category_id;

        @SerializedName("category_image")
        public String category_image;

        @SerializedName("category_name")
        public String category_name;

        @SerializedName("product_data")
        public List<PrdDataDatum> product_data = null;
    }

    public class PrdDataDatum {

        @SerializedName("category_id")
        public String category_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("product_name")
        public String product_name;
    }
}
