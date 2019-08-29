package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class AddCartNullSpinner {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("quantity")
    public String quantity;

    public AddCartNullSpinner(String productId, String prdQty) {
        this.product_id = productId;
        this.quantity = prdQty;
    }
}
