package com.nisarga.nisargaveggiez.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.Home.ProductDetailHome;
import com.nisarga.nisargaveggiez.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.List;

@NonReusable
@Layout(R.layout.order_details_item)
public class OrderDetailsItems {

    @View(R.id.ivitemIconOrdDetails)
    public ImageView ivitemIconOrdDetails;

    @View(R.id.tvPrdTitleOrdDetails)
    public TextView tvPrdTitleOrdDetails;

    @View(R.id.tv_qntyOrdDetails)
    public TextView tv_qntyOrdDetails;

    @View(R.id.tvpriceOldOrdDetails)
    public TextView tvpriceOldOrdDetails;

    @View(R.id.tvpriceNewOrdDetails)
    public TextView tvpriceNewOrdDetails;

    @View(R.id.tvcountItemsOrdDetails)
    public TextView tvcountItemsOrdDetails;


    @ParentPosition
    public int mParentPosition;

    public String murl;
    public String mnewPrice;
    public String moldPrice;
    public String mqty;
    public String mtitle;
    public String mordid;
    public String mord_prd_id;
    public Context mContext;
    public String str_priceValue,str_priceValue1;
    public String mWeight;

    public OrderDetailsItems(Context context, String ord_id, String ord_prd_id, String url, String title, String qty, String oldPrice,
                             String newPrice,String weight) {
        mContext = context;
        mordid = ord_id;
        mord_prd_id = ord_prd_id;
        murl = url;
        mtitle = title;
        mnewPrice = newPrice;
        moldPrice = oldPrice;
        mqty = qty;
        mWeight = weight;
    }
    public OrderDetailsItems(Context contxt)
    {
        mContext = contxt;
    }
    @Resolve
    public void onResolved() {

        //image------
        Glide.with(mContext).load(murl).into(ivitemIconOrdDetails);

        //---title--------
        tvPrdTitleOrdDetails.setText(mtitle);

        //Actual price------
        if (mnewPrice != null && !mnewPrice.isEmpty() && !mnewPrice.equals("null")) {
            double dbl_Price_1 = Double.parseDouble(mnewPrice);//need to convert string to decimal
            str_priceValue = String.format("%.2f", dbl_Price_1);//display only 2 decimal places of price
            tvpriceNewOrdDetails.setText("\u20B9" + " " + str_priceValue);
        }
        else {
            tvpriceNewOrdDetails.setText("\u20B9" + " " +"00.00");

        }

        //------old price-------
        if (moldPrice != null && !moldPrice.isEmpty() && !moldPrice.equals("null")) {

            double dbl_Price_2 = Double.parseDouble(moldPrice);//need to convert string to decimal
            str_priceValue1 = String.format("%.2f", dbl_Price_2);//display only 2 decimal places of price
            tvpriceOldOrdDetails.setVisibility(android.view.View.VISIBLE);
            tvpriceOldOrdDetails.setText("\u20B9" + " " + str_priceValue1);

        }
        else {
            tvpriceOldOrdDetails.setVisibility(android.view.View.INVISIBLE);
        }

        //-----weight-------
        if (mWeight != null && !mWeight.isEmpty() && !mWeight.equals("null")) {
            tv_qntyOrdDetails.setText(mWeight);
        }
        else {
            tv_qntyOrdDetails.setVisibility(android.view.View.INVISIBLE);
        }

        //quantity--------------------
        if (!mqty.equals("null") && !mqty.isEmpty()) {
            tvcountItemsOrdDetails.setText("Quantity" + " " + ":" + " " + mqty);

        }
        else {
            tvcountItemsOrdDetails.setText("Quantity" + " " + ":" + " " + "0");
        }
    }


}