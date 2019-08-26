package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDetails
{

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

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

    public CustomerDetails(String customer_id,String firstname,String lastname,String address_1,String city,String country_id,String zone_id,String company,String address_2,String postcode,String telephone,String email,String customer_group_id)
    {
        this.customer_id = customer_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address_1 = address_1;
        this.city = city;
        this.zone_id = zone_id;
        this.company = company;
        this.address_2 = address_2;
        this.postcode = postcode;
        this.telephone = telephone;
        this.email = email;
        this.customer_group_id = customer_group_id;
    }

}
