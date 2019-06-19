package com.app.ecommerce.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsModel {


    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("result")
    @Expose
    public List<Datum> result = null;

    public ProductDetailsModel(String id) {
        this.id = id;
    }

    public static class Datum {
        @SerializedName("product_id")
        @Expose
        public String product_id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("quantity")
        @Expose
        public String quantity;

        @SerializedName("image")
        @Expose
        public String image;

        @SerializedName("price")
        @Expose
        public String price;




        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}