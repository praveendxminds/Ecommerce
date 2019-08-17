package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailModel {

    @SerializedName("order_product_id")
    public  String order_product_id;

    @SerializedName("order_id")
    public String order_id;

    @SerializedName("status")
    public String mdstatus;

    @SerializedName("message")
    public  String mdmessage;

    @SerializedName("result")
    public List<OrderDetailDatum> result=null;

    public OrderDetailModel(String cust_id,String ord_id)
    {
        this.order_product_id=cust_id;
        this.order_id=ord_id;
    }

    public class OrderDetailDatum{

        @SerializedName("order_product_id")
        public String mdorder_product_id;

        @SerializedName("order_id")
        public String mdorder_id;

        @SerializedName("product_id")
        public String mdproduct_id;

        @SerializedName("name")
        public String mdname;

        @SerializedName("model")
        public String mdmodel;

        @SerializedName("quantity")
        public String mdquantity;

        @SerializedName("price")
        public String mdprice;

        @SerializedName("total")
        public String mdtotal;

        @SerializedName("tax")
        public String mdtax;

        @SerializedName("reward")
        public String mdreward;


    }
}
