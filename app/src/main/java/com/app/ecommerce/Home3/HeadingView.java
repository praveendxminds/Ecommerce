package com.app.ecommerce.Home3;

import android.content.Context;
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
@Layout(R.layout.feed_heading)
public class HeadingView {

    @View(R.id.headingTxt)
    private TextView headingTxt;

    @View(R.id.toggleIcon)
    private ImageView toggleIcon;

    @View(R.id.itemIcon)
    private CircularImageView imageView;

    @Toggle(R.id.toggleView)
    private LinearLayout toggleView;

    @ParentPosition
    private int mParentPosition;

    private Context mContext;
    private String mHeading;
    private String mCatImgUrl;

    public HeadingView(Context context, String heading,String CatImgUrl) {
        mContext = context;
        mHeading = heading;
        mCatImgUrl = CatImgUrl;
    }

    @Resolve
    private void onResolved() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
        headingTxt.setText(mHeading);
        Glide.with(mContext).load(mCatImgUrl).into(imageView);
       // imageView.setImageDrawable(mDrawable);
    }

    @Expand
    private void onExpand(){
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.down));
    }

    @Collapse
    private void onCollapse(){
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
    }
}
