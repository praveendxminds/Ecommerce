package com.nisarga.nisargaveggiez.notifications;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.nisarga.nisargaveggiez.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    String mUrl,mday,mtime,mdate,morderTitle,mordercontent;
//    public  NotificationItem(Context context)
//    {
//        mContext=  context;
//    }

    public NotificationItem(Context context,String dte,String orderTitle,String ordercontent)
    {
        mContext = context;
        mdate = dte;
        morderTitle = orderTitle;
        mordercontent = ordercontent;
    }

    @Resolve
    public void onResolved()
    {

        orderTitleNotifi.setText(morderTitle);
        orderDateNotifi.setText(mordercontent);
        timeNotifi.setText(mdate);



       /* Glide.with(mContext).load(mUrl).into(itemIconNotifi);
        dayNotifi.setText(mday);
        timeNotifi.setText(mtime);
        orderTitleNotifi.setText(morderTitle);
        orderNameNotifi.setText(morderName);
        orderDateNotifi.setText(mdate);
*/

    }

   /* @Click(R.id.notyfi_lt)
    public void onCardClick()
    {
        Intent myIntent = new Intent(mContext, ProductDetails_act.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }*/

}
