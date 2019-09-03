package com.nisarga.nisargaveggiez.Home;

import com.google.gson.annotations.SerializedName;

public class UpdateToCartOptionsModel {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("product_option_id")
    public String product_option_id;

    @SerializedName("product_option_value_id")
    public String product_option_value_id;

    @SerializedName("quantity")
    public String quantity;

    public UpdateToCartOptionsModel(String product_id,String product_option_id,String product_option_value_id, String sQuantity) {
        this.product_option_value_id = product_option_value_id;
        this.product_id = product_id;
        this.product_option_id = product_option_id;
        this.quantity = sQuantity;
    }
}
