package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by praveen on 29/11/18.
 */

public class User {



    @SerializedName("status")
    public String status;


    public User(String status) {
        this.status = status;
    }


}

