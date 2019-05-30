package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by praveen on 29/11/18.
 */

public class RegisterUser {


    @SerializedName("status")
    public String status;


    public RegisterUser(String status) {
        this.status = status;
    }



}

