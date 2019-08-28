package com.nisarga.nisargaveggiez.adapter;

/**
 * Created by praveen on 28/11/18.
 */

public class ProductData {

    private String product_id;
    private String name;
    private String image;
    private String meta_title;
    private Object meta_keyword;

    public String getPrdId() {
        return product_id;
    }

    public void setPrdId(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getmeta_title()
    {
        return meta_title;
    }

    public void setmeta_title(String meta_title) {
        this.meta_title = meta_title;
    }


    public Object getKeyword()
    {
        return meta_keyword;
    }

    public void setKeyword(Object meta_keyword) {
        this.meta_keyword = meta_keyword;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String prdImg) {
        this.image = prdImg;
    }
}
