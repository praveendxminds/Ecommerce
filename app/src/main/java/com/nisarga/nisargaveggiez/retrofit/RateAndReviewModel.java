package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateAndReviewModel {

    @SerializedName("customer_id")
    public String customer_id;

    public RateAndReviewModel(String custId)
    {
        this.customer_id = custId;
    }

    @SerializedName("status")
    public String  status;

    @SerializedName("message")
    public String  message;

    @SerializedName("like_rate_in_percent")
    public String  like_rate_in_percent;

    @SerializedName("nuber_of_users_rating")
    public int  nuber_of_users_rating;

    @SerializedName("rated")
    public String  rated;

    @SerializedName("feedback")
    public String  feedback;

    @SerializedName("result")
    public List<ReviewDatum> result=null;

    public class ReviewDatum {

        @SerializedName("customer_id")
        public String customer_id;

        @SerializedName("firstname")
        public String firstname;

        @SerializedName("lastname")
        public String lastname;

        @SerializedName("feedback")
        public String feedback;
    }
}
