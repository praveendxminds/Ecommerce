package com.nisarga.nisargaveggiez.Home;

import com.google.gson.annotations.SerializedName;

public class UpdateToCartModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("quantity")
    public String quantity;

    public UpdateToCartModel(String product_id, String sQuantity) {
        this.product_id = product_id;
        this.quantity = sQuantity;
    }
}
