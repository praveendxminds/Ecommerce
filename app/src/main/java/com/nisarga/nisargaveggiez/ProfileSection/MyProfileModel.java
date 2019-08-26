package com.nisarga.nisargaveggiez.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProfileModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<Datum> resultdata = null;

    public MyProfileModel(String custid) {
        this.customer_id = custid;
    }

    public class Datum {

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("firstname")
        public String firstname;

        @SerializedName("lastname")
        public String lastname;

        @SerializedName("address_1")
        public String address_1;

        @SerializedName("city")
        public String city;

        @SerializedName("postcode")
        public String postcode;

        @SerializedName("email")
        public String email;

        @SerializedName("telephone")
        public String telephone;

        @SerializedName("door")
        public String door;

        @SerializedName("apartment")
        public String apartment;

        @SerializedName("image")
        public String image;

        @SerializedName("nearby")
        public String nearby;

        @SerializedName("apartment_id")
        public String apartment_id;
    }
}
