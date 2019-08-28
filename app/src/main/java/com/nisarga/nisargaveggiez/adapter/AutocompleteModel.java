package com.nisarga.nisargaveggiez.adapter;

import java.util.ArrayList;

public class AutocompleteModel {
    private String title = "";
    private String imgurl = "";
    private int product_id = 0;
    private String metatitle = "";
    Object keywords;

    public AutocompleteModel(String title, String imgurl, int product_id,String metatitle,Object keywords) {
        this.title = title;
        this.imgurl = imgurl;
        this.product_id = product_id;
        this.metatitle = metatitle;
        this.product_id = product_id;
        this.keywords = keywords;

    }

    public String getTitle()
    {
        return title;
    }

            public void setTitle(String title) {
        this.title = title;
    }


    public String getMetatitle()
    {
        return metatitle;
    }

    public void setMetatitle(String metatitle) {
        this.metatitle = metatitle;
    }

    public Object getkeywords()
    {
        return keywords;
    }

    public void setkeywords(Object keywords) {
        this.keywords = keywords;
    }


    public String getImgUrl() {
        return imgurl;
    }

     public void setImgUrl(String imgurl) {
        this.imgurl = imgurl;
    }

     public int getproduct_id()
     {
        return product_id;
     }

     public void setproduct_id(int product_id)
     {
        this.product_id = product_id;
     }


            @Override
    public String toString() {
        return this.title;
    }
}