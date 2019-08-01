package com.app.ecommerce.ProfileSection;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApartmentList {

    @SerializedName("status")
    public String status;

    @SerializedName("data")
    public List<ApartmentListDatum> data=null;

    public class ApartmentListDatum{
        @SerializedName("apartment_id")
        public String apartment_id;

        @SerializedName("name")
        public String  name;

        @SerializedName("address")
        public String  address;

        @SerializedName("pincode")
        public String pincode;

        @SerializedName("status")
        public String  status;


    }
}
