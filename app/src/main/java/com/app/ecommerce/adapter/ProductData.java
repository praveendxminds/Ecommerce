package com.app.ecommerce.adapter;

/**
 * Created by praveen on 28/11/18.
 */

public class ProductData {

    private String product_id;
    private String name;
    private String image;

    public String getPrdId() {
        return product_id;
    }

    public void setPrdId(String prd_id) {
        this.product_id = prd_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String prdName) {
        this.name = prdName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String prdImg) {
        this.image = prdImg;
    }
}
