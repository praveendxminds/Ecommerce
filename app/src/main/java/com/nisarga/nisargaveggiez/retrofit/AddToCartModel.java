package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddToCartModel {

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("quantity")
    public String quantity;

    @SerializedName("option")
    @Expose
    private ArrayList<String> option;

    public AddToCartModel(String prd_id, String qty, ArrayList<String> addoption) {
        this.product_id = prd_id;
        this.quantity = qty;
        this.option = addoption;
    }

    @SerializedName("status")
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
