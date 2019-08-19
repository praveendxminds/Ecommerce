package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoyalityPointsModel {

    @SerializedName("customer_id")
    public String customer_id;

    public LoyalityPointsModel(String custId) {
        this.customer_id = custId;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public String data;

    @SerializedName("note")
    public List<DatumLP> note;

    public class DatumLP {

        @SerializedName("title")
        public String title;

        @SerializedName("desciption")
        public String desciption;
    }
}
