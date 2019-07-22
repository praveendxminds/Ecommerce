package com.app.ecommerce.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Modified by sushmita on 12/07/2019.
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

    @View(R.id.canceledOrder)
    public TextView canceledOrder;

    @View(R.id.deliveredOrder)
    public TextView deliveredOrder;

    @View(R.id.orderIdOrder)
    public TextView orderIdOrder;

    @View(R.id.deliveryDateOrder)
    public TextView deliveryDateOrder;

    @View(R.id.btnCancelOrder)
    public Button btnCancelOrder;

    @View(R.id.btnReOrder)
    public Button btnReOrder;

    @View(R.id.btnDetails)
    public Button btnDetails;

    /**
     * created by sushmita on 23rd June 2019
     */
    public Context mContext;
    public String mstatus, morderId, mdeliveryDate, mcancel;

    public orderItem(Context context) {
        mContext = context;
    }

    public orderItem(Context context, String orderId, String deliveryDate, String status, String cancel) {
        mContext = context;
        mstatus = status;
        morderId = orderId;
        mdeliveryDate = deliveryDate;
        mcancel = cancel;
    }

    @Resolve
    public void onResolved(){
        orderIdOrder.setText("Order Id :" + " " + morderId);
        Date localTime = null;
        try {
            localTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault()).parse(mdeliveryDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM d'th'''yy");
        String delivDate = sdf.format(new Date(localTime.getTime()));
        deliveryDateOrder.setText("Delivered on" + " " +delivDate );
        pendingOrder.setText(mstatus);
        btnCancelOrder.setText(mcancel);

    }

    @Click(R.id.llMyorder1)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, OrderDetial.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

}

