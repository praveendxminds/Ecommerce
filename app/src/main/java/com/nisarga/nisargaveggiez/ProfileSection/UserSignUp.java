package com.nisarga.nisargaveggiez.ProfileSection;

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
    public String smobile;

    @SerializedName("email")
    public String semail;

    @SerializedName("password")
    public String spassword;

    @SerializedName("apartment_name")
    public String sapartment_name;

    @SerializedName("block")
    public String sblock;

    @SerializedName("floor")
    public String sfloor;

    @SerializedName("door")
    public String sdoor;

    @SerializedName("area")
    public String sarea;

    @SerializedName("address")
    public String saddress;

    @SerializedName("pincode")
    public String spincode;

    @SerializedName("city")
    public String scity;

    @SerializedName("nearby")
    public String snearby;

    @SerializedName("apartment_id")
    public String sapartment_id;

    @SerializedName("landmark")
    public String slandmark;

    @SerializedName("profile")
    public String sprofile;

    public UserSignUp(String fname, String lname, String mobile, String email, String passwd, String apartment_name,
                      String block, String floor, String door, String area, String address, String pincode,
                      String city, String nearby, String apartment_id, String landmark, String profile) {

        this.sfirstname = fname;
        this.slastname = lname;
        this.smobile = mobile;
        this.semail = email;
        this.spassword = passwd;
        this.sapartment_name = apartment_name;
        this.sblock = block;
        this.sfloor = floor;
        this.sdoor = door;
        this.sarea = area;
        this.saddress = address;
        this.spincode = pincode;
        this.scity = city;
        this.snearby = nearby;
        this.sapartment_id = apartment_id;
        this.slandmark = landmark;
        this.sprofile = profile;
    }
}
