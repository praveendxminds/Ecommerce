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

@NonReusable
@Layout(R.layout.reorder_item_list)
public class ReorderItems {

    @View(R.id.ivitemIconReorder)
    public ImageView ivitemIconReorder;

    @View(R.id.tvPrdTitleReorder)
    public TextView tvPrdTitleReorder;

    @View(R.id.tv_qntyReorder)
    public TextView tv_qntyReorder;

    @View(R.id.tvpriceOldReorder)
    public TextView tvpriceOldReorder;

    @View(R.id.tvpriceNewReorder)
    public TextView tvpriceNewReorder;

    @View(R.id.tvcountItemsReorder)
    public TextView tvcountItemsReorder;

    @View(R.id.tvRevisedPriceReorder)
    public TextView tvRevisedPriceReorder;

    @ParentPosition
    public int mParentPosition;

    public String murl;
    public String mnewPrice;
    public String moldPrice;
    public String mqty;
    public String mtitle;
    public String mordid;
    public String mord_prd_id;
    public String mWeight;
    public String mRevisedPrice;
    //public String mCount;
    // public String mRevisedPrice;
    public Context mContext;
    public String str_priceValue, str_priceValue1, str_priceValue3;

    public ReorderItems(Context context, String ord_id, String ord_prd_id, String url, String title, String qty, String oldPrice,
                        String newPrice, String weight, String revisedPrice) {
        mContext = context;
        mordid = ord_id;
        mord_prd_id = ord_prd_id;
        murl = url;
        mtitle = title;
        mnewPrice = newPrice;
        moldPrice = oldPrice;
        mqty = qty;
        mWeight = weight;
        mRevisedPrice = revisedPrice;
    }

    public ReorderItems(Context contxt) {
        mContext = contxt;
    }

    @Resolve
    public void onResolved() {

        //-image-----
        if (murl != null && !murl.isEmpty() && !murl.equals("null")) {
            Glide.with(mContext).load(murl).into(ivitemIconReorder);
        }
        else
        {
            Glide.with(mContext).load(R.drawable.englishitem).into(ivitemIconReorder);
        }
        //---title--------
        if (mtitle != null && !mtitle.isEmpty() && !mtitle.equals("null")) {
            tvPrdTitleReorder.setText(mtitle);
        }
        else
        {
            tvPrdTitleReorder.setText("Product");
        }

        //Actual price---------
        if (mnewPrice != null && !mnewPrice.isEmpty() && !mnewPrice.equals("null")) {
            double dbl_Price_1 = Double.parseDouble(mnewPrice);//need to convert string to decimal
            str_priceValue = String.format("%.2f", dbl_Price_1);//display only 2 decimal places of price
            tvpriceNewReorder.setText("\u20B9" + " " + str_priceValue);
        } else {
            tvpriceOldReorder.setText("\u20B9" + " " + "00.00");
        }
        //discount price
        if (moldPrice != null && !moldPrice.isEmpty() && !moldPrice.equals("null")) {
            double dbl_Price_2 = Double.parseDouble(moldPrice);
            str_priceValue1 = String.format("%.2f", dbl_Price_2);
            tvpriceOldReorder.setText("\u20B9" + " " + str_priceValue1);
        } else {
            tvpriceOldReorder.setVisibility(android.view.View.INVISIBLE);
        }
        //----quantity----------
        if (!mqty.equals("null") && !mqty.isEmpty()) {
            tvcountItemsReorder.setText("Quantity" + " " + ":" + " " + mqty);
        } else {
            tvcountItemsReorder.setText("Quantity" + " " + ":" + " " + "0");
        }
        //-----weight-------------
        if (!mWeight.equals("null")) {
            tv_qntyReorder.setText(mWeight);
        } else {
            tv_qntyReorder.setVisibility(android.view.View.INVISIBLE);
        }
        //------------revised price-------
        if (mRevisedPrice != null && !mRevisedPrice.isEmpty() && !mRevisedPrice.equals("null")) {
            double dbl_Price_3 = Double.parseDouble(mRevisedPrice);//need to convert string to decimal
            str_priceValue3 = String.format("%.2f", dbl_Price_3);//display only 2 decimal places of price
            tvRevisedPriceReorder.setText("\u20B9" + " " + str_priceValue3);
        }
        else
        {
            tvRevisedPriceReorder.setText("00.00");
        }

    }
}