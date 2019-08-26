package com.nisarga.nisargaveggiez.Home;

import com.google.gson.annotations.SerializedName;

public class UpdateToCartModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("key")
    public String cart_id;

    @SerializedName("quantity")
    public String quantity;

    public UpdateToCartModel(String sCartId, String sQuantity) {
        this.cart_id = sCartId;
        this.quantity = sQuantity;
    }
}
