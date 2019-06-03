package com.app.ecommerce.retrofit;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by praveen on 14/11/18.
 */




public class MultipleResources {



    @SerializedName("items")
    public List<categories> data = null;
   /* @SerializedName("slider")
    public List<imageslider> resultdata=null;*/

    public class categories  {

        @SerializedName("ID")
        public int id;

        @SerializedName("category")
        public String name;

        @SerializedName("prdImg")
        public String product_url;



    }
    /*public class imageslider{
        @SerializedName("ID")
        public int id_img;

        @SerializedName("category")
        public String name_img;

        @SerializedName("prdImg")
        public String product_url_img;

    }*/





}
