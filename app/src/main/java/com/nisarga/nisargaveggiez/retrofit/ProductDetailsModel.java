package com.nisarga.nisargaveggiez.retrofit;

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

    public ProductDetailsModel(String cust_id, String prd_id) {
        this.customer_id = cust_id;
        this.product_id = prd_id;
    }

    public static class Datum {
        @SerializedName("product_id")
        @Expose
        public String product_id;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("wishlist_status")
        public String wishlist_status;

        @SerializedName("Add_product_quantity_in_cart")
        public String add_prd_qty;

        @SerializedName("quantity")
        @Expose
        public String quantity;

        @SerializedName("image")
        @Expose
        public List<ImageArr> image = null;

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

        @SerializedName("description")
        public String desc;

        public class ImageArr {

            @SerializedName("image")
            public String image_1;

            public String getImage() {
                return image_1;
            }

            public void setImage(String image) {
                this.image_1 = image;
            }
        }


        public String getProduct_id() {
            return product_id;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWishlistStatus() {
            return wishlist_status;
        }

        public void setWishlistStatus(String status) {
            this.wishlist_status = status;
        }
        public String getAddPrdQty() {
            return add_prd_qty;
        }

        public void setAddPrdQty(String qty_status) {
            this.add_prd_qty = qty_status;
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

        public List<ImageArr> getImageArr() {
            return image;
        }

        public void setImageArr(List<ImageArr> imageArr) {
            this.image = imageArr;
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

        public String getDiscount() {
            return discount_price;
        }

        public void setDiscount(String discount) {
            this.discount_price = discount;
        }
    }
}
