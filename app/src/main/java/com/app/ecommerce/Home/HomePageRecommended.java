package com.app.ecommerce.Home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.app.ecommerce.R;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
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

    public Context mContext;
    public List<ProductslHomePage.DealOfDayList> mImageList;

    public HomePageRecommended(Context context, List<ProductslHomePage.DealOfDayList> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Resolve
    public void onResolved() {
        placeholderviewRecommended.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for(ProductslHomePage.DealOfDayList image : mImageList) {
            placeholderviewRecommended.addView(new HomePageDealOfDayItemList(mContext, placeholderviewRecommended, image.image,image.name,image.price,image.qty));
        }
    }
}