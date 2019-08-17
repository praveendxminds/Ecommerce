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
   //public String mCount;
   // public String mRevisedPrice;
    public Context mContext;
    public String str_priceValue,str_priceValue1;

    public ReorderItems(Context context, String ord_id,String ord_prd_id, String url, String title, String qty, String oldPrice,
                        String newPrice) {
        mContext = context;
        mordid = ord_id;
        mord_prd_id = ord_prd_id;
        murl = url;
        mtitle = title;
        mnewPrice = newPrice;
        moldPrice = oldPrice;
        mqty = qty;
    }
    public ReorderItems(Context contxt)
    {
        mContext = contxt;
    }
    @Resolve
    public void onResolved() {

        Glide.with(mContext).load("http:\\/\\/3.213.33.73\\/Ecommerce\\/upload\\/image\\/catalog\\/Carrots Norwich.jpg").into(ivitemIconReorder);
        tvPrdTitleReorder.setText(mtitle);
        //tv_qntyReorder.setText(mqty);

        //double dbl_Price_1 = Double.parseDouble(mnewPrice);//need to convert string to decimal
        //str_priceValue = String.format("%.2f",dbl_Price_1);//display only 2 decimal places of price
        tvpriceNewReorder.setText("\u20B9" + " " + mnewPrice);

        //double dbl_Price_2 = Double.parseDouble(moldPrice);//need to convert string to decimal
        //str_priceValue1 = String.format("%.2f",dbl_Price_2);//display only 2 decimal places of price
        tvpriceOldReorder.setText("\u20B9" + " " + moldPrice);

        tvcountItemsReorder.setText("Quantity"+" "+":"+" "+mqty);

        //tvRevisedPriceReorder.setText("\u20B9" + " " +mRevisedPrice);

    }
    @Click(R.id.cardReorder)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        //myIntent.putExtra("prd_id", mordid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

}