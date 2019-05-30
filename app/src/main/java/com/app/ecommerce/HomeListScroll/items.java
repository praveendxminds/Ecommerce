package com.app.ecommerce.HomeListScroll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by janisharali on 21/08/16.
 */
public class items {

    @SerializedName("url")
    @Expose
    private String imageUrl;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("qty")
    @Expose
    private String qty;


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
