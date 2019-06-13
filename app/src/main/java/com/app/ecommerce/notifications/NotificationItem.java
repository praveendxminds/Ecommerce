package com.app.ecommerce.notifications;

import android.content.Context;
import android.content.Intent;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.productDetial;
import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.notification_items)
public class NotificationItem {

    @View(R.id.imageView)
    public CircularImageView imageView;

    public Context mContext;

    public NotificationItem(Context context) {
        mContext = context;
    }

    @Resolve
    public void onResolved()
    {

    }

    @Click(R.id.notyfi_lt)
    public void onCardClick()
    {
        Intent myIntent = new Intent(mContext, productDetial.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

}
