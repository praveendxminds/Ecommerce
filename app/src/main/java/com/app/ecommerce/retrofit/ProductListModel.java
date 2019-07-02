package com.app.ecommerce.retrofit;

import com.google.android.apps.common.proguard.SimpleEnum;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListModel {

    @SerializedName("status")
    public String status;

    @SerializedName("result")
    public List<ProductListDatum> result=null;

    public class ProductListDatum{

        @SerializedName("product_id")
        public String prd_id;

        @SerializedName("name")
        public String name;

        @SerializedName("quantity")
        public  String qty;

        @SerializedName("image")
        public String image;

        @SerializedName("price")
        public String price;
    }
}
