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

    @SerializedName("data")
    public List<TxnHistoryDatum> data=null;

    public class TxnHistoryDatum
    {
        @SerializedName("t_id")
        public String t_id;

        @SerializedName("date")
        public String date;

        @SerializedName("w_id")
        public String w_id;

        @SerializedName("transaction_type")
        public String transaction_type;

        @SerializedName("description")
        public String description;

        @SerializedName("amount")
        public String amount;

        @SerializedName("transaction_id")
        public String transaction_id;

        @SerializedName("closing_balance")
        public String closing_balance;


    }


}
