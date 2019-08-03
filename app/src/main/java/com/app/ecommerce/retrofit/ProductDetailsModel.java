package com.app.ecommerce.retrofit;

import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsModel {


    @SerializedName("product_id")
    @Expose
    public String product_id;

    @SerializedName("customer_id")
    @Expose
    public String customer_id;


    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("result")
    @Expose
    public List<Datum> result = null;

    public ProductDetailsModel(String cust_id,String prd_id) {
        this.customer_id=cust_id;
        this.product_id = prd_id;
    }

    public static class Datum {
        @SerializedName("product_id")
        @Expose
        public String product_id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("description")
        public String desc;

        @SerializedName("quantity")
        @Expose
        public String quantity;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("price")
        @Expose
        public String price;

        @SerializedName("reward")
        @Expose
        public String reward;

        @SerializedName("points")
        @Expose
        public String points;

        @SerializedName("reviews")
        @Expose
        public String reviews;

        @SerializedName("discount_price")
        @Expose
        public String discount_price;


        public String getProduct_id(){
            return product_id;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String sdesc) {
            this.desc = sdesc;
        }


        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getReward() {
            return reward;
        }

        public void setReward(String sreward) {
            this.reward = sreward;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String spoints) {
            this.points = spoints;
        }

        public String getReviews() {
            return reviews;
        }

        public void setReviews(String sreviews) {
            this.reviews = sreviews;
        }

        public String getDiscount()
        {
            return discount_price;
        }
        public void setDiscount(String discount)
        {
            this.discount_price=discount;
        }
    }
}
