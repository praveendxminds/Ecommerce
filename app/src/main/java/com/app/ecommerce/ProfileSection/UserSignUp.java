package com.app.ecommerce.ProfileSection;

import com.google.gson.annotations.SerializedName;

public class UserSignUp {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

//post parameters
    @SerializedName("firstname")
    public String sfirstname;

    @SerializedName("lastname")
    public String slastname;

    @SerializedName("mobile")
    public String smobile ;

    @SerializedName("email")
    public String  semail;

    @SerializedName("password")
    public String  spassword;

    @SerializedName("apartment_name")
    public String sapartment_name ;

    @SerializedName("block")
    public String sblock ;

    @SerializedName("floor")
    public String sfloor ;

    @SerializedName("door")
    public String  sdoor;

    @SerializedName("area")
    public String  sarea;

    @SerializedName("address")
    public String saddress ;

    @SerializedName("pincode")
    public String spincode ;

    @SerializedName("landmark")
    public String  slandmark;

    @SerializedName("city")
    public String  scity;

    public UserSignUp(String fname,String lname,String mobile,String email,
                      String passwd,String apartment_name,String block,
                      String floor,String door,String area,String address,
                        String landmark,String pincode,String city)
    {
        this.sfirstname=fname;
        this.slastname=lname;
        this.smobile=mobile;
        this.semail=email;
        this.spassword=passwd;
        this.sapartment_name=apartment_name;
        this.sblock = block;
        this.sfloor = floor;
        this.sdoor = door;
        this.sarea = area;
        this.saddress = address;
        this.spincode = pincode;
        this.slandmark = landmark;
        this.scity=city;
    }


}
