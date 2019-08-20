package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;


@NonReusable
@Layout(R.layout.home_page_recommended_for_you)
public class HomePageRecommended {

    @View(R.id.placeholderviewRecommended)
    public PlaceHolderView placeholderviewRecommended;

    public TextView mtextCartItemCount;
    public Context mContext;
    public List<ProductslHomePage.RecommendedList> mImageList;
    SessionManager session;

    public HomePageRecommended(Context context, TextView textCartItemCount, List<ProductslHomePage.RecommendedList> imageList) {
        mContext = context;
        mImageList = imageList;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        placeholderviewRecommended.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for (ProductslHomePage.RecommendedList image : mImageList) {
            placeholderviewRecommended.addView(new HomePageRecommendedItemList(mContext, mtextCartItemCount, placeholderviewRecommended,
                    image.product_id, image.image, image.name, image.price, image.discount_price, image.quantity));
        }
    }

    @Click(R.id.tvSeeAllRecomnd)
    public void onClick() {
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}