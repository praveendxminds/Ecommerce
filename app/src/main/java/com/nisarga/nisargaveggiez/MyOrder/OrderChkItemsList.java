package com.nisarga.nisargaveggiez.MyOrder;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Resolve;
import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Layout(R.layout.chk_items_list)
public class OrderChkItemsList {

    @View(R.id.tvPrdTitle)
    public TextView tvPrdTitle;

    @View(R.id.tvPrdWeight)
    public TextView tvPrdWeight;

    @View(R.id.tvPrdQty)
    public TextView tvPrdQty;

    @View(R.id.tvPrdPrice)
    public TextView tvPrdPrice;

    @View(R.id.tvUpdatedPrice)
    public TextView tvUpdatedPrice;

    String str_priceValue,str_priceValue1;
    public Context mContext;
    public String mOrdId,mOrdPrdId,mPrdTitle,mPrdWeight,mPrdQty,mPrdPrice,mPrdUpdatedPrice;
    public OrderChkItemsList(Context contxt)
    {
        this.mContext = contxt;
    }
    public OrderChkItemsList(Context contxt,String ordId,String ordPrdId,String prd_title,String prd_weight,
                             String prd_qty,String prd_price,String updated_price)
    {
        this.mContext = contxt;
        this.mOrdId = ordId;
        this.mOrdPrdId = ordPrdId;
        this.mPrdTitle = prd_title;
        this.mPrdWeight = prd_weight;
        this.mPrdQty = prd_qty;
        this.mPrdPrice = prd_price;
        this.mPrdUpdatedPrice = updated_price;
    }

    @Resolve
    public void onResolved() {


        //---title--------
        tvPrdTitle.setText(mPrdTitle);
        //Actual price---------
        if (mPrdPrice != null && !mPrdPrice.isEmpty() && !mPrdPrice.equals("null")) {
            double dbl_Price_1 = Double.parseDouble(mPrdPrice);//need to convert string to decimal
            str_priceValue = String.format("%.2f", dbl_Price_1);//display only 2 decimal places of price
            tvPrdPrice.setText("\u20B9" + " " + str_priceValue);
        } else {
            tvPrdPrice.setText("\u20B9" + " " + "00.00");
        }
        //discount price
        if (mPrdUpdatedPrice != null && !mPrdUpdatedPrice.isEmpty() && !mPrdUpdatedPrice.equals("null")) {
            double dbl_Price_2 = Double.parseDouble(mPrdUpdatedPrice);
            str_priceValue1 = String.format("%.2f", dbl_Price_2);
            tvUpdatedPrice.setText("\u20B9" + " " + str_priceValue1);
        } else {
            tvUpdatedPrice.setText("\u20B9" + " " +"00.00");
        }
        //----quantity----------
        if (!mPrdQty.equals("null") && !mPrdQty.isEmpty()) {
            tvPrdQty.setText("Quantity" + " " + ":" + " " + mPrdQty);
        } else {
            tvPrdQty.setText("Quantity" + " " + ":" + " " + "0");
        }
        //-----weight-------------
        if (!mPrdWeight.equals("null")&& !mPrdQty.isEmpty()) {
            tvPrdWeight.setText(mPrdWeight);
        } else {
            tvPrdWeight.setVisibility(android.view.View.INVISIBLE);
        }

    }

}
