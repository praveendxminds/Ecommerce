package com.nisarga.nisargaveggiez.billing;

import com.google.gson.annotations.SerializedName;
import com.nisarga.nisargaveggiez.retrofit.OrderDetailModel;

import java.util.List;



    public class AddOrder
    {

        @SerializedName("delivery_date")
        public  String delivery_date;

        @SerializedName("success")
        public  String success;

        @SerializedName("status")
        public  String status;

        @SerializedName("data")
        public List<AddOrderDatum> data=null;

        public AddOrder(String delivery_date)
        {
            this.delivery_date=delivery_date;
        }

        public class AddOrderDatum
        {
//            @SerializedName("address")
//            public String address;

//            @SerializedName("savings")
//            public Integer savings;

            @SerializedName("delivery_charges")
            public String delivery_charges;

//            @SerializedName("total")
//            public Integer total;

        }

    }
