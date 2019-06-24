package com.app.ecommerce.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.my_orders_list)
public class orderItem {

    @View(R.id.itemIconOrder)
    public CircularImageView itemIconOrder;

    @View(R.id.statusOrder)
    public TextView statusOrder;

    @View(R.id.pendingOrder)
    public TextView pendingOrder;

    @View(R.id.orderIdOrder)
    public TextView orderIdOrder;

    @View(R.id.deliveryDateOrder)
    public TextView deliveryDateOrder;

   @View(R.id.btnCancelOrder)
   public Button btnCancelOrder;

   @View(R.id.btnReOrder)
   public Button btnReOrder;

   @View(R.id.btnDetails)
   public  Button btnDetails;

    /**
     * created by sushmita on 23rd June 2019
     */
    public Context mContext;
    public String mUrl,mstatus,morderId,mdeliveryDate;
    public  orderItem(Context context)
    {
        mContext=  context;
    }

    public orderItem(Context context,String url,String status,String orderId,String deliveryDate) {
        mContext = context;
        mUrl = url;
        mstatus = status;
        morderId = orderId;
        mdeliveryDate = deliveryDate;
    }

   /* @Resolve
    public void onResolved()
    {
        Glide.with(mContext).load(mUrl).into(itemIconOrder);
        statusOrder.setText(mstatus);
        orderIdOrder.setText(morderId);
        deliveryDateOrder.setText(mdeliveryDate);
    }*/
    @Click(R.id.myOrder_lt)
    public void onCardClick()
    {
        Intent myIntent = new Intent(mContext, OrderDetial.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

}
