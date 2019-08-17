package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WebPagesModel {


    @SerializedName("id")
    @Expose
    public String id;

    public WebPagesModel(String id) {
        this.id = id;
    }


    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public  String message;


    public void setResult(List<DatumContact> result) {
        this.result = result;
    }

    @SerializedName("result")
    public List<DatumContact> result=null;

    public class DatumContact{

        @SerializedName("title")
        public String stitle;

        @SerializedName("desciption")
        public String desc;

        public String getDesc() {
            return desc;
        }
    }
}
