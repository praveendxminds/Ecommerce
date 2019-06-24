package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderList {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public  String message;

    @SerializedName("result")
    public List<MyOrderListDatum> result=null;

    public class MyOrderListDatum{

        @SerializedName("order_id")
        public String order_id;

        @SerializedName("firstname")
        public String firstname;

        @SerializedName("lastname")
        public String lastname;

        @SerializedName("date_added")
        public String date_added;

        @SerializedName("status")
        public String status;

        @SerializedName("total")
        public String total;

        @SerializedName("currency_code")
        public String currency_code;

        @SerializedName("currency_value")
        public String currency_value;

        @SerializedName("cancel")
        public String cancel;


    }
}
