package com.app.ecommerce.Home2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;

/**
 * Created by praveen on 14/11/18.
 */

@Parent
@SingleTop
@Layout(R.layout.home_two_category_item)
public class HomeTwoCategoryItem {

    @View(R.id.headingTxt)
    private TextView headingTxt;

   @View(R.id.toggleIcon)
    private ImageView toggleIcon;

    @View(R.id.itemIcon)
    private ImageView imageView;

    @Toggle(R.id.productItem)
    private CardView productItem;

    @ParentPosition
    private int mParentPosition;

    private Context mContext;
    private String mHeading;
    private String mCatImgUrl;

    public HomeTwoCategoryItem(Context context, String heading, String CatImgUrl) {
        mContext = context;
        mHeading = heading;
        mCatImgUrl = CatImgUrl;
    }

    @Resolve
    private void onResolved() {
       // toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
        headingTxt.setText(mHeading);
        Glide.with(mContext).load(mCatImgUrl).into(imageView);
       // imageView.setImageDrawable(mDrawable);
    }

    @Click(R.id.productItem)
    private void onLongClick(){
        Intent intent = new Intent(mContext, HomeTwoCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Expand
    private void onExpand(){
       // toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.down));
    }

    @Collapse
    private void onCollapse(){
       // toggleIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
    }
}
