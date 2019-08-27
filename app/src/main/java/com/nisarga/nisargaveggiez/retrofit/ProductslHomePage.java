package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductslHomePage {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("status")
    public String status;

    @SerializedName("profile_pic")
    public String profile_pic;

    @SerializedName("banner")
    public List<BannerList> banner = null;

    @SerializedName("recommended")
    public List<RecommendedList> recommended = null;

    @SerializedName("dealoftheday")
    public List<DealOfDayList> dealoftheday = null;

    @SerializedName("products")
    public List<Products> products = null;

    public ProductslHomePage(String cust_id) {
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

        @SerializedName("Add_product_quantity_in_cart")
        public String add_product_quantity_in_cart;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("weight_classes")
        public Object weight_classes;

        @SerializedName("options")
        public Object options;

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

        @SerializedName("Add_product_quantity_in_cart")
        public String add_product_quantity_in_cart;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("weight_classes")
        public Object weight_classes;

        @SerializedName("options")
        public Object options;
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

        @SerializedName("Add_product_quantity_in_cart")
        public String add_product_quantity_in_cart;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("weight_classes")
        public Object weight_classes;

        @SerializedName("options")
        public Object options;

    }
}

