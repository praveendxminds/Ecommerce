package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpImageResponse {

    @SerializedName("status")
    public String status;

    @SerializedName("file")
    public String image;

    @SerializedName("files")
    public String profile_url;

    public SignUpImageResponse(String img) {
        this.image = img;
    }
}
