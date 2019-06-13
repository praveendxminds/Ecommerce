package com.app.ecommerce.Home3;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by praveen on 14/11/18.
 */

public class Feed {

    @SerializedName("HomeTwoCategory")
    @Expose
    public String heading;


    @SerializedName("prdImg")
    @Expose
    public String CategoryImgUrl;


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }



    public String getCatImageUrl() {
        return CategoryImgUrl;
    }

    public void setCatImageURL(String CategoryImgUrl) {
        this.CategoryImgUrl = CategoryImgUrl;
    }


}
