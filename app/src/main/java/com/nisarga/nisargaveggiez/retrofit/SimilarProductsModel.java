package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarProductsModel {

    @SerializedName("product_id")
    public String   product_id;

    public SimilarProductsModel(String prd_id)
    {
        this.product_id=prd_id;
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

        @SerializedName("date_added")
        public String date_added;

        @SerializedName("date_modified")
        public String date_modified;

        @SerializedName("manufacturer_id")
        public String manufacturer_id;

        @SerializedName("viewed")
        public String viewed;

        @SerializedName("status")
        public String status;

        @SerializedName("location")
        public String location ;

        @SerializedName("weight")
        public String weight ;

        @SerializedName("length")
        public String  length;

        @SerializedName("width")
        public String width;

        @SerializedName("height")
        public String  height;

        @SerializedName("subtract")
        public String subtract ;

        @SerializedName("minimum")
        public String  minimum;

        @SerializedName("sort_order")
        public String  sort_order;

        @SerializedName("name")
        public String  name;

        @SerializedName("description")
        public String description ;

        @SerializedName("tag")
        public String  tag;


    }

}
