package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("quantity")
    public String quantity;

    @SerializedName("product_option_id")
    public String product_option_id;

    @SerializedName("product_option_value_id")
    public String product_option_value_id;

    @SerializedName("cart_id")
    public String cart_id;
    
    public AddToCartModel(String productId, String prdQty, String option_id, String option_value_id) {
        this.product_id = productId;
        this.quantity = prdQty;
        this.product_option_id = option_id;
        this.product_option_value_id = option_value_id;
    }
}
