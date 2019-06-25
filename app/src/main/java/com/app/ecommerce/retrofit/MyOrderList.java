package com.app.ecommerce.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderList extends  java.util.Date{

    @SerializedName("customer_id")
    public  String scustomer_id;

    @SerializedName("status")
    public String mstatus;

    @SerializedName("message")
    public  String msg;

    @SerializedName("result")
    public List<MyOrderListDatum> result=null;

    public MyOrderList(String cust_id)
    {
        this.scustomer_id=cust_id;
    }

    public class MyOrderListDatum{

        @SerializedName("order_id")
        public String sorder_id;

        @SerializedName("firstname")
        public String sfirstname;

        @SerializedName("lastname")
        public String slastname;

        @SerializedName("date_added")
        public String sdate_added;

        @SerializedName("status")
        public String sstatus;

        @SerializedName("total")
        public String stotal;

        @SerializedName("currency_code")
        public String currency_code;

        @SerializedName("currency_value")
        public String currency_value;

        @SerializedName("cancel")
        public String scancel;


    }
}
