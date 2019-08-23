package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDetails {

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

    @SerializedName("telephone")
    public String telephone;

    @SerializedName("email")
    public String email;

    @SerializedName("customer_group_id")
    public String customer_group_id;

}
