package com.nisarga.nisargaveggiez.notifications;

import com.google.gson.annotations.SerializedName;
import com.nisarga.nisargaveggiez.retrofit.InsertWishListItems;

import java.util.List;

public class NotificationListModel
{

    @SerializedName("status")
    public  String status;

    @SerializedName("message")
    public String message;

    @SerializedName("customer_id")
    public String customer_id;


    public NotificationListModel(String customer_id)
    {
        this.customer_id=customer_id;
    }

    @SerializedName("data")
    public List<NotificationListModelDatum> result=null;

    public class NotificationListModelDatum
    {

        @SerializedName("pn_id")
        public String pn_id;

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("title")
        public String title;

        @SerializedName("body")
        public String body;

        @SerializedName("date")
        public String date;

    }


}
