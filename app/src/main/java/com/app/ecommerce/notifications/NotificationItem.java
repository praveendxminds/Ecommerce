package com.app.ecommerce.notifications;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.ProductDetails_act;
import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.notification_items)
public class NotificationItem {

    @View(R.id.itemIconNotifi)
    public CircularImageView itemIconNotifi;

    @View(R.id.dayNotifi)
    public TextView dayNotifi;

    @View(R.id.timeNotifi)
    public TextView timeNotifi;

    @View(R.id.orderTitleNotifi)
    public TextView orderTitleNotifi;

    @View(R.id.orderDateNotifi)
    public TextView orderDateNotifi;

    @View(R.id.iv_warning)
    public ImageView iv_warning;



    public Context mContext;
    String mUrl,mday,mtime,mdate,morderTitle,morderName;
    public  NotificationItem(Context context)
    {
        mContext=  context;
    }

    public NotificationItem(Context context,String url,String day,String time,String orderTitle,String orderName,String date) {
        mContext = context;
        mUrl = url;
        mdate = day;
        mtime = time;
        morderTitle = orderTitle;
        morderName = orderName;
        mdate = date;
    }

    @Resolve
    public void onResolved()
    {
       /* Glide.with(mContext).load(mUrl).into(itemIconNotifi);
        dayNotifi.setText(mday);
        timeNotifi.setText(mtime);
        orderTitleNotifi.setText(morderTitle);
        orderNameNotifi.setText(morderName);
        orderDateNotifi.setText(mdate);
*/

    }

    @Click(R.id.notyfi_lt)
    public void onCardClick()
    {
        Intent myIntent = new Intent(mContext, ProductDetails_act.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

}
