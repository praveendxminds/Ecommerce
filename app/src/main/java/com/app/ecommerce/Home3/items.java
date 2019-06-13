package com.app.ecommerce.Home3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by janisharali on 21/08/16.
 */
public class items {

    @SerializedName("url")
    @Expose
    public String imageUrl;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("price")
    @Expose
    public String price;

    @SerializedName("qty")
    @Expose
    public String qty;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getItemTitle() {
        return title;
    }

    public void setItemTitle(String title) {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getQuantity() {
        return qty;
    }

    public void setQuantity(String qty) {
        this.qty = qty;
    }


}
