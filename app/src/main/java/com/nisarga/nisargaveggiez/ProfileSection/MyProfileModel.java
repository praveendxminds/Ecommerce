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

        @SerializedName("address_2")
        public String address_2;

        @SerializedName("city")
        public String city;

        @SerializedName("postcode")
        public String postcode;

        @SerializedName("area")
        public String area;

        @SerializedName("zone_id")
        public String zone_id;

        @SerializedName("email")
        public String email;

        @SerializedName("telephone")
        public String telephone;

        @SerializedName("apartment")
        public String apartment;

        @SerializedName("door")
        public String door;



    }
}
