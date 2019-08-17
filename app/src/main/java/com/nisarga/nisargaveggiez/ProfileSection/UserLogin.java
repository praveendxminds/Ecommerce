package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserLogin {


    @SerializedName("user")
    public String user;

    @SerializedName("password")
    public String password;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<Datum> resultdata = null;

    public UserLogin(String usr, String password) {
        this.user = usr;
        this.password = password;
    }

    public class Datum {

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("customer_group_id")
        public String customer_group_id;

        @SerializedName("firstname")
        public String firstname;

        @SerializedName("lastname")
        public String lastname;

        @SerializedName("email")
        public String email;

        @SerializedName("cart")
        public String cart;

        @SerializedName("telephone")
        public String telephone;

        @SerializedName("wishlist")
        public String wishlist;

        @SerializedName("address_id")
        public String address_id;

        @SerializedName("date_added")
        public String date_added;

        @SerializedName("api_token")
        public String api_token;

    }
}
