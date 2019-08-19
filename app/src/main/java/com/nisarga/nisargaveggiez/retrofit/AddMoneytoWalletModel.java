package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

public class AddMoneytoWalletModel {

    @SerializedName("customer_id")
    public String customer_id;

    @SerializedName("amount")
    public String amount;

    @SerializedName("transaction_id")
    public String transaction_id;

    public AddMoneytoWalletModel(String cust_id, String amnt, String txn_id) {
        this.customer_id = cust_id;
        this.amount = amnt;
        this.transaction_id = txn_id;
    }

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String message;
}
