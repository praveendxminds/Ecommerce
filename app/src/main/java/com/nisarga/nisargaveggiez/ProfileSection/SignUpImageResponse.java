package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpImageResponse {

    @SerializedName("status")
    public String status;

    @SerializedName("uploaded_file[]")
    public String image;

    @SerializedName("result")
    public List<Datum> resultdata = null;

    public SignUpImageResponse(String img) {
        this.image = img;
    }

    public class Datum {

        @SerializedName("image0")
        public String image0;

    }
}
