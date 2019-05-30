package com.app.ecommerce.MyOrder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

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
@Layout(R.layout.order_items)
public class orderItem {

    @View(R.id.imageView)
    private CircularImageView imageView;

    private Drawable mDrawable;
    private Context mContext;

    public orderItem(Context context,Drawable drawable) {
        mContext = context;
        mDrawable = drawable;
    }

    @Resolve
    private void onResolved() {
        imageView.setImageDrawable(mDrawable);
    }

    @Click(R.id.ord_lt)
    public void onCardClick()
    {
        Intent myIntent = new Intent(mContext, OrderDetial.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

}
