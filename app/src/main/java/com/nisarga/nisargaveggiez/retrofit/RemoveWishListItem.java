package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class RemoveWishListItem {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("count")
    public Integer count;

    public RemoveWishListItem(String cust_id, String prd_id) {
        this.customer_id = cust_id;
        this.product_id = prd_id;
    }
}
