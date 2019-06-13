package com.app.ecommerce.Home3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

import com.app.ecommerce.R;

/**
 * Created by praveen on 13/11/18.
 */


@Parent
@SingleTop
@Layout(R.layout.banner_item)
public class Banner {


    @View(R.id.banner)
    public ImageView imageView;




    public Drawable mDrawable;
    public Context mContext;
    public String mUrl;

    public Banner(Context context,String url)
    {
        mContext = context;
        mUrl = url;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(mUrl).into(imageView);
       // imageView.setImageDrawable(mDrawable);
    }



}
