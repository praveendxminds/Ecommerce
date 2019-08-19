package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

public class NavEditImage {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("profile_pic")
    public String image;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    public NavEditImage(String customerId, String profile_pic) {
        this.customer_id = customerId;
        this.image = profile_pic;
    }
}
