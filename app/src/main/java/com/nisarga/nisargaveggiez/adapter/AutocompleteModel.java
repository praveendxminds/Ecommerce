package com.nisarga.nisargaveggiez.adapter;

public class AutocompleteModel {
    private String title = "";
    private String imgurl = "";
    private int id = 0;

    public AutocompleteModel(String title, String imgurl, int id) {
        this.title = title;
        this.imgurl = imgurl;
        this.id = id;

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

     public int getId()
     {
        return id;
     }

     public void setId(int id)
     {
        this.id = id;
     }


            @Override
    public String toString() {
        return this.title;
    }
}