package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.R;

/**
 * Created by sushmita
 */

@NonReusable
@Layout(R.layout.homepage_layouttwo_item)
public class HomePageLayoutTwoItem {

    @View(R.id.ivImage)
    public ImageView ivImage;

    @View(R.id.tvTitle)
    public TextView tvTitle;

    Context mContext;
    String mId;
    String mImage;
    String mTitle;

    public HomePageLayoutTwoItem(Context context, String imageUrl, String id, String title) {

        this.mContext = context;
        this.mImage = imageUrl;
        this.mId = id;
        this.mTitle = title;
    }

    @Resolve
    public void onResolved() {
        if (mImage != null && !mImage.isEmpty() && !mImage.equals("null")) {
            Glide.with(mContext).load(mImage).into(ivImage);
        } else {
            Glide.with(mContext).load(R.drawable.englishitem).into(ivImage);
        }

        if (mTitle != null && !mTitle.isEmpty() && !mTitle.equals("null")) {
            tvTitle.setText(mTitle);
        } else {
            tvTitle.setVisibility(android.view.View.GONE);
        }

    }

    @Click(R.id.cardViewLT2)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, HomeCategory.class);
        myIntent.putExtra("ViewAll", mId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }
}
