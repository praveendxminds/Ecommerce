package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class EditProfileModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("firstname")
    public String  firstname;

    @SerializedName("lastname")
    public String  lastname;

    @SerializedName("email")
    public String  email;

    @SerializedName("phn")
    public String phn ;

    @SerializedName("password")
    public String  password;

    @SerializedName("apartment")
    public String  apartment;

    @SerializedName("door")
    public String door ;

    @SerializedName("area")
    public String  area;

    @SerializedName("address_1")
    public String  address_1;


    @SerializedName("address_2")
    public String  address_2;

    @SerializedName("pincode")
    public String  pincode;


    public EditProfileModel(String mcustId,String mfname,String mlname,String memail,String mphn,String passwd,String aptmnt,
                            String doorno,String area,String addrss_1,String addrss_2,String spincode)
    {
        this.customer_id = mcustId;
        this.firstname = mfname;
        this.lastname = mlname;
        this.email = memail;
        this.phn = mphn;
        this.password = passwd;
        this.apartment = aptmnt;
        this.door = doorno;
        this.area = area;
        this.address_1 = addrss_1;
        this.address_2 = addrss_2;
        this.pincode = spincode;

    }

    @SerializedName("status")
    public String status ;

    @SerializedName("message")
    public String  message;


}
