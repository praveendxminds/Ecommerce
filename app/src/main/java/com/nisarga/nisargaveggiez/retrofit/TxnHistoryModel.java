package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TxnHistoryModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;

    public TxnHistoryModel(String custId)
    {
        this.customer_id = custId;
    }

    @SerializedName("data")
    public List<TxnHistoryDatum> data=null;

    public class TxnHistoryDatum
    {

        @SerializedName("date")
        public String date;

        @SerializedName("transaction_type")
        public String transaction_type;

        @SerializedName("description")
        public String description;

        @SerializedName("amount")
        public String amount;

        @SerializedName("balance")
        public String balance;

        @SerializedName("type")
        public String type;


    }


}
