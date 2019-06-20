package com.app.ecommerce.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserLogin {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("data")
    public List<Datum> resultdata = null;

    public UserLogin(String email, String password) {
        this.email = email;
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

        @SerializedName("wishlist")
        public String wishlist;

        @SerializedName("address_id")
        public String address_id;

        @SerializedName("date_added")
        public String date_added;

    }
}
