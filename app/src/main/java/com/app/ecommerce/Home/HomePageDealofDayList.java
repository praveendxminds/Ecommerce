package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.app.ecommerce.R;
import com.app.ecommerce.productDetial;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;


@NonReusable
@Layout(R.layout.home_page_deal_of_day)
public class HomePageDealofDayList {

    @View(R.id.placeholderviewDealofDay)
    private PlaceHolderView placeholderviewDealofDay;

    private Context mContext;
    private List<ProductslHomePage.DealOfDayList> mImageList;

    public HomePageDealofDayList(Context context, List<ProductslHomePage.DealOfDayList> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Resolve
    private void onResolved() {
        placeholderviewDealofDay.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for(ProductslHomePage.DealOfDayList image : mImageList) {
            placeholderviewDealofDay.addView(new HomePageDealOfDayItemList(mContext, placeholderviewDealofDay, image.url,image.title,image.price,image.qty));
        }
    }
    @Click(R.id.dealofdayClick)
    private void onClick(){
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


}