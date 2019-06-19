package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWishList {

    @SerializedName("customer_id")
    public String id;

    @SerializedName("status")
    public String status;

    @SerializedName("count")
    public int count;

    @SerializedName("result")
    public List<WishListDatum> result = null;

    public GetWishList(String mid) {
        this.id = mid;
    }

    public class WishListDatum {

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

    }
}
