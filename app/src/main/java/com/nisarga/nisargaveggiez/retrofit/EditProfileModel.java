package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class EditProfileModel {

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

    @SerializedName("email")
    public String email;

    @SerializedName("phn")
    public String phn;

    @SerializedName("old_password")
    public String old_password;

    @SerializedName("new_password")
    public String new_password;

    @SerializedName("apartment")
    public String apartment;

    @SerializedName("door")
    public String door;

    @SerializedName("address_1")
    public String address;

    @SerializedName("city")
    public String city;

    @SerializedName("pincode")
    public String pincode;

    @SerializedName("nearby")
    public String nearby;

    @SerializedName("apartmentId")
    public String apartmentId;

    @SerializedName("profile_pic")
    public String profile_pic;

    public EditProfileModel(String mcustId, String mfname, String mlname, String memail, String mphn, String passwd,
                            String confPass, String aptmnt, String doorno, String addrss, String scity,
                            String spincode, String snearby, String apartment_id, String sprofile_pic) {

        this.customer_id = mcustId;
        this.firstname = mfname;
        this.lastname = mlname;
        this.email = memail;
        this.phn = mphn;
        this.old_password = passwd;
        this.new_password = confPass;
        this.apartment = aptmnt;
        this.door = doorno;
        this.address = addrss;
        this.city = scity;
        this.pincode = spincode;
        this.nearby = snearby;
        this.apartmentId = apartment_id;
        this.profile_pic = sprofile_pic;
    }
}
