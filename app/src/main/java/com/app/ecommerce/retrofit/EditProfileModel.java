package com.app.ecommerce.retrofit;

import com.app.ecommerce.EditProfile;
import com.google.gson.annotations.SerializedName;

public class EditProfileModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("name")
    public String  name;

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

    @SerializedName("pincode")
    public String  pincode;


    public EditProfileModel(String mcustId,String mname,String memail,String mphn,String passwd,String aptmnt,
                            String doorno,String area,String address,String spincode)
    {
        customer_id = mcustId;
        name = mname;
        email = memail;
        phn = mphn;
        password = passwd;
        apartment = aptmnt;
        door = doorno;
        area = area;
        address_1 = address;
        pincode = spincode;

    }

    @SerializedName("status")
    public String status ;

    @SerializedName("message")
    public String  message;


}
