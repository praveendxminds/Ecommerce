package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

public class AddToCartModel {

    @SerializedName("product_id")
    public String  product_id;

    @SerializedName("quantity")
    public String  quantity;

    public AddToCartModel(String prd_id,String qty)
    {
        this.product_id = prd_id;
        this.quantity = qty;
    }
    @SerializedName("status")
    public String  status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
