package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReorderItemsModel {

    //post prarams
    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("order_id")
    public String order_id;

    public ReorderItemsModel(String custid, String orderid)
    {
        this.customer_id = custid;
        this.order_id = orderid;
    }

//-------------------
    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public List<ReorderResult> result = null;

    @SerializedName("TotalProduct")
    public String TotalProduct;

    @SerializedName("totalMoney")
    public String totalMoney;

    @SerializedName("customer_details")
    public List<ReorderCustDetails> customer_details = null;

    public class ReorderResult {

        @SerializedName("order_id")
        public String order_id;

        @SerializedName("order_product_id")
        public String order_product_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("price")
        public String price;

        @SerializedName("tax")
        public String tax;

        @SerializedName("currency_code")
        public String currency_code;


    }

    public class ReorderCustDetails {

        @SerializedName("invoice_prefix")
        public String invoice_prefix;

        @SerializedName("invoice_no")
        public String invoice_no;

        @SerializedName("order_id")
        public String order_id;

        @SerializedName("firstname")
        public String firstname;

        @SerializedName("lastname")
        public String lastname;

        @SerializedName("shipping_address_1")
        public String shipping_address_1;

        @SerializedName("shipping_address_2")
        public String shipping_address_2;

        @SerializedName("shipping_city")
        public String shipping_city;

        @SerializedName("date_added")
        public String date_added;

        @SerializedName("shipping_zone")
        public String shipping_zone;

        @SerializedName("delivery_date")
        public String delivery_date;

        @SerializedName("telephone")
        public String telephone;

        @SerializedName("apartment")
        public String apartment;

        @SerializedName("delivery_instruction")
        public String delivery_instruction;


    }

}