package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductslHomePage {

    @SerializedName("id")
    public String id;

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("status")
    public String string;

    @SerializedName("banner")
    public List<BannerList> banner = null;

    @SerializedName("recommended")
    public List<RecommendedList> recommended = null;

    @SerializedName("dealoftheday")
    public List<DealOfDayList> dealoftheday = null;

    @SerializedName("products")
    public List<Products> products = null;

    public ProductslHomePage(String id,String cust_id) {
        this.id = id;
        this.customer_id = cust_id;
    }

    public class BannerList {

        @SerializedName("title")
        public String title;

        @SerializedName("image")
        public String image;

        @SerializedName("link")
        public String link;
    }

    public class RecommendedList {

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("discount_price")
        public String discount_price;
    }

    public class DealOfDayList {

        @SerializedName("product_id")
        public String prd_id;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public String qty;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("discount_price")
        public String discount_price;
    }

    public class Products {

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;

        @SerializedName("discount_price")
        public String discount_price;
    }
}

