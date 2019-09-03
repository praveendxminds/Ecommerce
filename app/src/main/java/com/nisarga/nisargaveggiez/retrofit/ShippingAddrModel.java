package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingAddrModel {

    @SerializedName("firstname")
    public String firstname;

    @SerializedName("lastname")
    public String lastname;

    @SerializedName("address_1")
    public String address_1;

    @SerializedName("city")
    public String city;

    @SerializedName("country_id")
    public String country_id;

    @SerializedName("zone_id")
    public String zone_id;

    @SerializedName("company")
    public String company;

    @SerializedName("address_2")
    public String address_2;

    @SerializedName("postcode")
    public String postcode;

    @SerializedName("custom_field")
    public String custom_field;

    public ShippingAddrModel(String str_fname,String str_lname,String str_area,
                             String str_city,String str_country_id,String str_zone_id,
                             String str_company,String str_addrs,String str_postcode,String str_instruct)
    {
        this.firstname = str_fname;
        this.lastname = str_lname;
        this.address_1 = str_area;
        this.city = str_city;
        this.country_id = str_country_id;
        this.zone_id = str_zone_id;
        this.company = str_company;
        this.address_2 = str_addrs;
        this.postcode = str_postcode;
        this.custom_field = str_instruct;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("total")
    public String total;

    @SerializedName("total_savings")
    public String total_savings;

    @SerializedName("delivery_charge")
    public String delivery_charge;

    @SerializedName("total_payable")
    public String total_payable;

    @SerializedName("data")
    public List<ShippingDatum> data=null;

    public class ShippingDatum {

        @SerializedName("firstname")
        public String firstname;

        @SerializedName("lastname")
        public String lastname;

        @SerializedName("address_1")
        public String address_1;

        @SerializedName("city")
        public String city;

        @SerializedName("country_id")
        public String country_id;

        @SerializedName("zone_id")
        public String zone_id;

        @SerializedName("company")
        public String company;

        @SerializedName("address_2")
        public String address_2;

        @SerializedName("postcode")
        public String postcode;

        @SerializedName("custom_field")
        public String custom_field;
    }
}
