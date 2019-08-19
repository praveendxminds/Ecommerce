package com.nisarga.nisargaveggiez.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartListModel {


    @SerializedName("products")
    public List<CartListDatum> result = null;

    @SerializedName("vouchers")
    public List<VouchersDatum> vouchers = null;

    @SerializedName("totals")
    public List<TotalsDatum> totals = null;


    public CartListModel() {
    }

    public class CartListDatum {

        @SerializedName("cart_id")
        public String cart_id;

        @SerializedName("product_id")
        public String product_id;

        @SerializedName("image")
        public String image;

        @SerializedName("name")
        public String name;

        @SerializedName("discount_price")
        public String discount_price;

        @SerializedName("model")
        public String model;

        @SerializedName("option")
        public List<DatumOption> option = null;

        @SerializedName("quantity")
        public String quantity;

        @SerializedName("stock")
        public String stock;

        @SerializedName("shipping")
        public String shipping;

        @SerializedName("price")
        public String price;

        @SerializedName("total")
        public String total;

        @SerializedName("reward")
        public String reward;

        public class DatumOption {

            @SerializedName("product_option_id")
            public String product_option_id;

            @SerializedName("product_option_value_id")
            public String product_option_value_id;

            @SerializedName("name")
            public String name;

            @SerializedName("value")
            public String value;

            @SerializedName("type")
            public String type;

        }
    }

    public class VouchersDatum {

    }

    public class TotalsDatum {

        @SerializedName("title")
        public Integer title;

        @SerializedName("text")
        public Integer text;
    }


}
