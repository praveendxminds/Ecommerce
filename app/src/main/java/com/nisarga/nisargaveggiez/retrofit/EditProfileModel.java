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

    @SerializedName("apartment")
    public String apartment;

    @SerializedName("pincode")
    public String pincode;

    @SerializedName("address_1")
    public String address;

    @SerializedName("address_2")
    public String address_2;

    @SerializedName("password")
    public String password;

    @SerializedName("door")
    public String door;

    @SerializedName("area")
    public String area;

    public EditProfileModel(String mcustId, String mfname, String mlname, String memail, String mphn, String aptmnt,
                            String spincode, String addrss, String passwd, String doorno, String area) {

        this.customer_id = mcustId;
        this.firstname = mfname;
        this.lastname = mlname;
        this.email = memail;
        this.phn = mphn;
        this.apartment = aptmnt;
        this.pincode = spincode;
        this.address = addrss;
        this.password = passwd;
        this.door = doorno;
        this.area = area;
    }
}
