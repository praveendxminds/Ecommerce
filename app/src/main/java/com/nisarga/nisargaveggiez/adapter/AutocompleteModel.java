package com.nisarga.nisargaveggiez.adapter;

public class AutocompleteModel {
    private String title = "";
    private String imgurl = "";
    private int product_id = 0;

    public AutocompleteModel(String title, String imgurl, int product_id) {
        this.title = title;
        this.imgurl = imgurl;
        this.product_id = product_id;

    }

    public String getTitle()
    {
        return title;
    }

            public void setTitle(String title) {
        this.title = title;
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