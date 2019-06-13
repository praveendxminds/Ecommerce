package com.app.ecommerce.Home3;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import com.app.ecommerce.R;

/**
 * Created by praveen on 14/11/18.
 */

@Parent
@SingleTop
@Layout(R.layout.home_three_feed_heading)
public class HeadingView {

    @View(R.id.headingTxt)
    public TextView headingTxt;

    @View(R.id.toggleIcon)
    public ImageView toggleIcon;

    @View(R.id.itemIcon)
    public CircularImageView imageView;

    @Toggle(R.id.toggleView)
    public LinearLayout toggleView;

    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public String mHeading;
    public String mCatImgUrl;

    public HeadingView(Context context, String heading,String CatImgUrl) {
        mContext = context;
        mHeading = heading;
        mCatImgUrl = CatImgUrl;
    }

    @Resolve
    public void onResolved() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
        headingTxt.setText(mHeading);
        Glide.with(mContext).load(mCatImgUrl).into(imageView);
       // imageView.setImageDrawable(mDrawable);
    }

    @Expand
    public void onExpand(){
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.down));
    }

    @Collapse
    public void onCollapse(){
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
    }
}
