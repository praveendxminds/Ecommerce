package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class AddMoneytoWalletModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("amount")
    public String amount;

    @SerializedName("transaction_id")
    public String transaction_id;

    @SerializedName("status1")
    public String status_param;

    @SerializedName("description")
    public String description;

    public AddMoneytoWalletModel(String cust_id,  String txn_id,String amnt,String str_status,String desc) {
        this.customer_id = cust_id;
        this.amount = amnt;
        this.transaction_id = txn_id;
        this.status_param = str_status;
        this.description = desc;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;
}
